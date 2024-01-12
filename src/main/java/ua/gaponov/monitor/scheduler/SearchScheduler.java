package ua.gaponov.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchScheduler {

    @Scheduled(cron = "0 0 12 * * *")
    public void searchTerminals() {

    }
}
