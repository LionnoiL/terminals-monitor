package ua.gaponov.monitor.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DatePeriod implements Serializable {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
