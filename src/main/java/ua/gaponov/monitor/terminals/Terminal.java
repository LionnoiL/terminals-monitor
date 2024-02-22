package ua.gaponov.monitor.terminals;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"armId"})
@Table(name = "terminals")
@NoArgsConstructor
public class Terminal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arm_id", unique = true)
    private Long armId;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "cash_register_name")
    private String cashRegisterName;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "last_update")
    private String lastUpdate;

    @Column(name = "total_heap_size")
    private Long totalHeapSize;

    @Column(name = "free_heap_size")
    private Long freeHeapSize;

    @Column(name = "used_heap_size")
    private Long usedHeapSize;
}
