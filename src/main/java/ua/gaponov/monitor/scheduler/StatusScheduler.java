package ua.gaponov.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusScheduler {

    @Scheduled(cron = "0 * * * * *")
    public void updateTerminals() {

    }
}
