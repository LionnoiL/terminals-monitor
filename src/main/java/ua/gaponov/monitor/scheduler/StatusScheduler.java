package ua.gaponov.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.net.NetUtils;
import ua.gaponov.monitor.terminals.TerminalDTO;
import ua.gaponov.monitor.terminals.TerminalInfo;
import ua.gaponov.monitor.terminals.TerminalService;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusScheduler {

    private final TerminalService terminalService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void updateTerminals() {
        log.debug("start update terminals");
        for (TerminalDTO terminal : terminalService.getAllTerminals()) {
            TerminalInfo terminalInfo = NetUtils.getTerminalInfo("http://" + terminal.getIpAddress() + ":5555/echo");
            if (terminalInfo != null) {
                terminal.setLastUpdate(terminalInfo.getLastUpdate());
                terminal.setArmId(terminalInfo.getArmId());
                terminal.setShopName(terminalInfo.getShopName());
                terminal.setCashRegisterName(terminalInfo.getCashRegisterName());
                log.debug("terminal with ip:{} active",terminal.getIpAddress());
            } else {
                log.debug("terminal with ip:{} not active",terminal.getIpAddress());
            }
            terminalService.save(terminal);
        }
        log.debug("end update terminals");
    }
}
