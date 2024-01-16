package ua.gaponov.monitor.terminals;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.gaponov.monitor.errors.NotFoundException;
import ua.gaponov.monitor.net.AddressService;
import ua.gaponov.monitor.net.NetUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TerminalService {

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
        checkAddressList(addressService.getAddressRange("192.168.11."));
        checkAddressList(addressService.getAddressRange("192.168.12."));
        checkAddressList(addressService.getAddressRange("192.168.13."));
        System.out.println("search end");
    }

    @Transactional
    private void checkAddressList(List<String> addressRange) {
        for (String address : addressRange) {
            TerminalInfo terminalInfo = NetUtils.getTerminalInfo("http://" + address + ":5555/echo");
            if (terminalInfo != null) {
                Terminal terminal = null;
                try {
                    terminal = getByArmId(terminalInfo.getArmId());
                } catch (NoSuchElementException e) {
                    terminal = new Terminal();
                }

                terminal.setLastUpdate(terminalInfo.getLastUpdate());
                terminal.setArmId(terminalInfo.getArmId());
                terminal.setShopName(terminalInfo.getShopName());
                terminal.setCashRegisterName(terminalInfo.getCashRegisterName());
                terminal.setIpAddress(address);
                terminalRepository.save(terminal);
            }
        }
    }

    public void delete(Terminal terminal) {
        terminalRepository.delete(terminal);
    }

    public boolean executeCommand(Terminal terminal, TerminalCommands name) {
        return false;
    }
}
