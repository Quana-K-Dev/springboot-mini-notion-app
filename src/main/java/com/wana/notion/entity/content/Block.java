package com.wana.notion.entity.content;

import com.wana.notion.entity.common.BaseCreatedUpdatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"page"})
@Entity @Table(name = "blocks",
        indexes = @Index(name = "ix_blocks_page_order", columnList = "page_id,order_index"))
public class Block extends BaseCreatedUpdatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Convert(converter = BlockTypeConverter.class)
    @Column(nullable = false, length = 16)
    private BlockType type;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 0;

    @Column(name = "json_content", columnDefinition = "json") // MySQL JSON → map kiểu String
    private String jsonContent;

    @Column(name = "content_ref", length = 255) // liên kết ngoài (nếu offload nội dung)
    private String contentRef;
}
