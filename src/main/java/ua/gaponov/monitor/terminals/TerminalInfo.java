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
    @JsonProperty("total_heap_size")
    private String totalHeapSize = "0";
    @JsonProperty("free_heap_size")
    private String freeHeapSize = "0";
    @JsonProperty("used_heap_size")
    private String usedHeapSize = "0";

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
