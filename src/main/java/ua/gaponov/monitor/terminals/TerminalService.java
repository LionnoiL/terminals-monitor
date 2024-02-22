package ua.gaponov.monitor.terminals;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.gaponov.monitor.errors.NotFoundException;
import ua.gaponov.monitor.net.AddressService;
import ua.gaponov.monitor.net.NetUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TerminalService {

    @Value("${ip.address.ranges}")
    private List<String> addressRanges;
    private final TerminalRepository terminalRepository;
    private final TerminalMapper terminalMapper;
    private final AddressService addressService;

    public List<TerminalDTO> getAllTerminals() {
        List<Terminal> terminals = getAll();
        return terminals.stream().map(terminalMapper::mapEntityToDto).toList();
    }

    private List<Terminal> getAll() {
        return terminalRepository.findAllByOrderByArmId();
    }

    @Transactional
    public void save(TerminalDTO terminalDTO) {
        Terminal terminal = terminalMapper.mapDtoToEntity(terminalDTO);
        terminalRepository.save(terminal);
    }

    public Terminal getByArmId(Long id) throws NotFoundException {
        return terminalRepository.findByArmId(id).orElseThrow(() ->
                new NotFoundException("Терміналу з armId: [" + id + "] не існує в базі!"));
    }

    public TerminalDTO getTerminalDtoByArmId(Long id) {
        return terminalMapper.mapEntityToDto(getByArmId(id));
    }

    public void search() {
        log.debug("start search");
        for (String networkNumber : addressRanges) {
            checkAddressList(addressService.getAddressRange(networkNumber));
        }
        log.debug("end search");
    }

    @Transactional
    private void checkAddressList(List<String> addressRange) {
        for (String address : addressRange) {
            TerminalInfo terminalInfo = NetUtils.getTerminalInfo("http://" + address + ":5555/echo");
            if (terminalInfo != null) {
                Terminal terminal = null;
                try {
                    terminal = getByArmId(terminalInfo.getArmId());
                } catch (NotFoundException e) {
                    terminal = new Terminal();
                }

                terminal.setLastUpdate(terminalInfo.getLastUpdate());
                terminal.setArmId(terminalInfo.getArmId());
                terminal.setShopName(terminalInfo.getShopName());
                terminal.setCashRegisterName(terminalInfo.getCashRegisterName());
                terminal.setIpAddress(address);
                terminal.setTotalHeapSize(Long.valueOf(terminalInfo.getTotalHeapSize()));
                terminal.setFreeHeapSize(Long.valueOf(terminalInfo.getFreeHeapSize()));
                terminal.setUsedHeapSize(Long.valueOf(terminalInfo.getUsedHeapSize()));
                terminalRepository.save(terminal);
            }
        }
    }

    public void delete(Terminal terminal) {
        terminalRepository.delete(terminal);
    }

    public boolean executeCommand(Terminal terminal, TerminalCommands name) {
        return NetUtils.sendSimpleCommand("http://" + terminal.getIpAddress() + ":5555/command", name);
    }
}
