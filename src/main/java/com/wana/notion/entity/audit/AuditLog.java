package com.wana.notion.entity.audit;

import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"actor"})
@Entity @Table(name = "audit_logs",
        indexes = {
                @Index(name = "ix_audit_actor", columnList = "actor_user_id"),
                @Index(name = "ix_audit_entity", columnList = "entity_type,entity_id")
        })
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "actor_user_id")
    private User actor; // có thể null theo schema

    @Column(nullable = false, length = 100)
    private String action;

    @Column(name = "entity_type", length = 100)
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(length = 255)
    private String reason;

    @Column(name = "metadata_json", columnDefinition = "json") // JSON tự do
    private String metadataJson;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
