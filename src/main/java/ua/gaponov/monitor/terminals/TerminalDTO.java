package ua.gaponov.monitor.terminals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalDTO {

    private Long id;
    private Long armId;
    private String shopName;
    private String cashRegisterName;
    private String ipAddress;
    private String lastUpdate;
    private boolean active;
}
