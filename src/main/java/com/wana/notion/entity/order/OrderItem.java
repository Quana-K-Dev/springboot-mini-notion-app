package com.wana.notion.entity.order;

import com.wana.notion.entity.template.Template;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"order", "template"})
@Entity @Table(name = "order_items",
        uniqueConstraints = @UniqueConstraint(name = "uk_orderitem_order_template", columnNames = {"order_id","template_id"}),
        indexes = @Index(name = "ix_order_items_order", columnList = "order_id"))
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Column(name = "price_vnd", nullable = false)
    private Long priceVnd;

    @Column(nullable = false)
    private Integer quantity = 1;
}