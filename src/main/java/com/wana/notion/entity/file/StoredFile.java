package com.wana.notion.entity.file;

import com.wana.notion.entity.common.BaseCreatedEntity;
import com.wana.notion.entity.user.User;
import com.wana.notion.entity.workspace.Workspace;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"owner","workspace"})
@Entity @Table(name = "files",
        uniqueConstraints = @UniqueConstraint(name = "uk_files_object_key", columnNames = "object_key"),
        indexes = {
                @Index(name = "ix_files_owner", columnList = "owner_user_id"),
                @Index(name = "ix_files_workspace", columnList = "workspace_id")
        })
public class StoredFile extends BaseCreatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_user_id")
    private User owner; // nullable theo schema

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "workspace_id")
    private Workspace workspace; // nullable theo schema

    @Convert(converter = FileKindConverter.class)
    @Column(nullable = false, length = 16)
    private FileKind kind = FileKind.OTHER;

    @Column(name = "object_key", nullable = false, length = 255)
    private String objectKey; // key lưu trên S3/GCS

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;
}
