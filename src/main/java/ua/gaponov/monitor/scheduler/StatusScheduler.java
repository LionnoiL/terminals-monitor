package ua.gaponov.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.terminals.TerminalDTO;
import ua.gaponov.monitor.terminals.TerminalInfo;
import ua.gaponov.monitor.terminals.TerminalService;
import ua.gaponov.monitor.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class StatusScheduler {

    private final TerminalService terminalService;

    @Scheduled(cron = "0 * * * * *")
    public void updateTerminals() {
        for (TerminalDTO terminal : terminalService.getAllTerminals()) {
            TerminalInfo terminalInfo = HttpUtils.getTerminalInfo("http://"+terminal.getIpAddress()+":5555/echo");
            if (terminalInfo != null) {
                terminal.setLastUpdate(terminalInfo.getLastUpdate());
                terminal.setArmId(terminalInfo.getArmId());
                terminal.setShopName(terminalInfo.getShopName());
                terminal.setCashRegisterName(terminalInfo.getCashRegisterName());
                terminalService.save(terminal);
            }
        }
    }
}
