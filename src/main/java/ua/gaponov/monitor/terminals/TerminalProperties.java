package ua.gaponov.monitor.terminals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalProperties {

    int terminalId;
    String shopName;
    String shopAddress;
    String cashRegisterName;
    String fiscalName;
    String fiscalToken;
    String fiscalIp;
    double fiscalAutoPlusSum;
    String prostopayToken;
    int exchangeIntervalMin;
    boolean exchangeEnable;
}
