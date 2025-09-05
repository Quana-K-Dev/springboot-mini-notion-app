package com.wana.notion.entity.security;

import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user"})
@Entity @Table(name = "password_reset_tokens",
        uniqueConstraints = @UniqueConstraint(name = "uk_pwdreset_token", columnNames = "token"),
        indexes = @Index(name = "ix_pwdreset_user", columnList = "user_id"))
public class PasswordResetToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt; // CHECK (expires_at > created_at) ở DB

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now(); // entity-level default
}
