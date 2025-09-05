package com.wana.notion.entity.user;

import com.wana.notion.entity.common.BaseCreatedUpdatedEntity;
import com.wana.notion.entity.rbac.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"userRoles"}) // tránh toString đệ quy
@Entity @Table(name = "users",
        indexes = @Index(name = "ix_users_status", columnList = "status"),
        uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email"))
public class User extends BaseCreatedUpdatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 254) // theo chuẩn RFC/SMTP tối đa 254 ký tự
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "display_name", length = 80)
    private String displayName;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(length = 160)
    private String bio;

    @Convert(converter = UserStatusConverter.class) // có autoApply nhưng đặt tường minh để rõ ràng
    @Column(nullable = false, length = 32)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    // Giữ quan hệ 1..* tới UserRole để lưu granted_at; orphanRemoval=true để xóa liên kết mồ côi
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles;
}
