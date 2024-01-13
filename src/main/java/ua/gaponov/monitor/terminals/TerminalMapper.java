package ua.gaponov.monitor.terminals;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.net.NetUtils;
import ua.gaponov.monitor.utils.Mapper;

@RequiredArgsConstructor
@Component
public class TerminalMapper implements Mapper<Terminal, TerminalDTO> {

    @Override
    public TerminalDTO mapEntityToDto(Terminal source) throws RuntimeException {
        TerminalDTO terminal = new TerminalDTO();
        terminal.setId(source.getId());
        terminal.setArmId(source.getArmId());
        terminal.setShopName(source.getShopName());
        terminal.setCashRegisterName(source.getCashRegisterName());
        terminal.setIpAddress(source.getIpAddress());
        terminal.setLastUpdate(source.getLastUpdate());
        terminal.setActive(NetUtils.pingAddress(terminal.getIpAddress()));
        return terminal;
    }


    @Override
    public Terminal mapDtoToEntity(TerminalDTO source) throws RuntimeException {
        Terminal terminal = new Terminal();
        terminal.setId(source.getId());
        terminal.setArmId(source.getArmId());
        terminal.setShopName(source.getShopName());
        terminal.setCashRegisterName(source.getCashRegisterName());
        terminal.setIpAddress(source.getIpAddress());
        terminal.setLastUpdate(source.getLastUpdate());
        return terminal;
    }
}
