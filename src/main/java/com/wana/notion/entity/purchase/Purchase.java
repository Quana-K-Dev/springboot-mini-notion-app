package com.wana.notion.entity.purchase;

import com.wana.notion.entity.common.BaseCreatedEntity;
import com.wana.notion.entity.order.Order;
import com.wana.notion.entity.template.Template;
import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"buyer", "template", "sourceOrder"})
@Entity @Table(name = "purchases",
        uniqueConstraints = @UniqueConstraint(name = "uk_purchase_buyer_template", columnNames = {"buyer_user_id","template_id"}),
        indexes = {
                @Index(name = "ix_purchases_buyer", columnList = "buyer_user_id"),
                @Index(name = "ix_purchases_template", columnList = "template_id")
        })
public class Purchase extends BaseCreatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "buyer_user_id", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Column(name = "license_key", nullable = false, length = 36)
    private String licenseKey; // UUID

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "source_order_id")
    private Order sourceOrder;
}
