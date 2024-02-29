package ua.gaponov.monitor.terminals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalDTO {

    private int id;
    private int armId;
    private String shopName;
    private String cashRegisterName;
    private String ipAddress;
    private String lastUpdate;
    private boolean active;
    private Long totalHeapSize;
    private Long freeHeapSize;
    private Long usedHeapSize;
}
