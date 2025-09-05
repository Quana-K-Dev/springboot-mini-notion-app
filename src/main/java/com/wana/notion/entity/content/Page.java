package com.wana.notion.entity.content;

import com.wana.notion.entity.common.BaseCreatedUpdatedEntity;
import com.wana.notion.entity.workspace.Workspace;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"workspace", "parent", "children", "blocks"})
@Entity @Table(name = "pages",
        indexes = {
                @Index(name = "ix_pages_workspace_parent", columnList = "workspace_id,parent_page_id"),
                @Index(name = "ix_pages_archived", columnList = "is_archived"),
                @Index(name = "ix_pages_deleted", columnList = "deleted_at")
        })
public class Page extends BaseCreatedUpdatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_page_id")
    private Page parent; // cây phân cấp pages; ON DELETE SET NULL

    @OneToMany(mappedBy = "parent")
    private Set<Page> children;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @Column(length = 64)
    private String icon;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 0;

    @Column(nullable = false)
    private Short depth = 0; // CHECK 0..10 nằm ở DB

    @Column(name = "is_archived", nullable = false)
    private boolean archived = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Block> blocks;
}