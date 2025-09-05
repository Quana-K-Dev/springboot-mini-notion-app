package com.wana.notion.entity.workspace;

import com.wana.notion.entity.common.BaseCreatedUpdatedEntity;
import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"owner", "members"})
@Entity @Table(name = "workspaces",
        uniqueConstraints = @UniqueConstraint(name = "uk_workspace_owner_name", columnNames = {"owner_user_id","name"}),
        indexes = @Index(name = "ix_workspaces_owner", columnList = "owner_user_id"))
public class Workspace extends BaseCreatedUpdatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkspaceMember> members;
}