package ua.gaponov.monitor.report.deleted;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedOrderItemDTO {

    private LocalDateTime deletedTime;
    private String userName;
    private String productName;
    private double qty;
    private double summa;
}