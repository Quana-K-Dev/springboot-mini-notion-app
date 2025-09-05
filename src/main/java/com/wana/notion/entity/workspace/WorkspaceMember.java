package com.wana.notion.entity.workspace;

import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"workspace", "user"})
@Entity @Table(name = "workspace_members",
        indexes = @Index(name = "ix_member_user", columnList = "user_id"))
public class WorkspaceMember {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private WorkspaceMemberId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("workspaceId")
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Convert(converter = WorkspaceRoleConverter.class)
    @Column(nullable = false, length = 12)
    private WorkspaceRole role;

    @Column(name = "invited_at", nullable = false)
    private Instant invitedAt = Instant.now();
}
