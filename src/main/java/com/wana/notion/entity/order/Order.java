package com.wana.notion.entity.order;

import com.wana.notion.entity.common.BaseCreatedEntity;
import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"buyer", "items"})
@Entity @Table(name = "orders",
        indexes = {
                @Index(name = "ix_orders_buyer", columnList = "buyer_user_id"),
                @Index(name = "ix_orders_status", columnList = "status")
        })
public class Order extends BaseCreatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "buyer_user_id", nullable = false)
    private User buyer;

    @Column(name = "total_vnd", nullable = false)
    private Long totalVnd = 0L;

    @Enumerated(EnumType.STRING) // ENUM UPPERCASE → lưu STRING
    @Column(nullable = false, length = 12)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items;
}
