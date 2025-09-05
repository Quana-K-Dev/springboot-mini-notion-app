package com.wana.notion.entity.payment;

import com.wana.notion.entity.common.BaseCreatedEntity;
import com.wana.notion.entity.order.Order;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"order"})
@Entity @Table(name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_payments_order", columnNames = "order_id"),     // 1:1 với Order
                @UniqueConstraint(name = "uk_payments_txnref", columnNames = "vnp_txn_ref")  // VNPay ref unique
        },
        indexes = @Index(name = "ix_payments_status", columnList = "status"))
public class Payment extends BaseCreatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // enforced unique bằng constraint ở trên

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PaymentGateway gateway = PaymentGateway.VNPAY;

    @Column(name = "amount_vnd", nullable = false)
    private Long amountVnd;

    @Column(name = "vnp_txn_ref", nullable = false, length = 64)
    private String vnpTxnRef;

    @Column(name = "vnp_response_code", length = 8)
    private String vnpResponseCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "raw_callback", columnDefinition = "json") // raw payload IPN
    private String rawCallback;
}
