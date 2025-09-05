package com.wana.notion.entity.rbac;

import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user", "role"})
@Entity @Table(name = "user_roles") // PK là EmbeddedId; FK map bằng @MapsId
public class UserRole {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "granted_at", nullable = false)
    private Instant grantedAt = Instant.now(); // default ở app layer (DB cũng có default CURRENT_TIMESTAMP(6))
}