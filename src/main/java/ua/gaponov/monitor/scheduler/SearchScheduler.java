package ua.gaponov.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.terminals.TerminalService;

@Component
@RequiredArgsConstructor
public class SearchScheduler {

    private final TerminalService terminalService;

    @Scheduled(cron = "0 0 12 * * *")
    public void searchTerminals() {
        terminalService.search();
    }
}
