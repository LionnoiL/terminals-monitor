package ua.gaponov.monitor.terminals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalProperties {

    private int terminalId;
    private String shopName;
    private String shopAddress;
    private String cashRegisterName;
    private String fiscalName;
    private String fiscalToken;
    private String fiscalIp;
    private double fiscalAutoPlusSum;
    private String prostopayToken;
    private int exchangeIntervalMin;
    private boolean exchangeEnable;
    private int defaultMerchantId;
}
