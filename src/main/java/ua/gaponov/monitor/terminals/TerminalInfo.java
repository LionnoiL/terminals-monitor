package ua.gaponov.monitor.terminals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalInfo {

    @JsonProperty("arm_id")
    private Long armId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("shop_name")
    private String shopName;
    @JsonProperty("cash_register_name")
    private String cashRegisterName;
    @JsonProperty("last_update")
    private String lastUpdate;

    @Override
    public String toString() {
        return "TerminalInfo{" +
                "armId=" + armId +
                ", name='" + name + '\'' +
                ", shopName='" + shopName + '\'' +
                ", cashRegisterName='" + cashRegisterName + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}