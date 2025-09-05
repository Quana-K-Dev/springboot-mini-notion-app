package com.wana.notion.entity.template;

import com.wana.notion.entity.common.BaseCreatedUpdatedEntity;
import com.wana.notion.entity.content.Page;
import com.wana.notion.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"seller", "previewPage", "tags"})
@Entity @Table(name = "templates",
        uniqueConstraints = @UniqueConstraint(name = "uk_templates_slug", columnNames = "slug"),
        indexes = {
                @Index(name = "ix_templates_seller", columnList = "seller_user_id"),
                @Index(name = "ix_templates_status", columnList = "status")
        })
public class Template extends BaseCreatedUpdatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "seller_user_id", nullable = false)
    private User seller;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 210)
    private String slug;

    @Lob // text dài
    private String description;

    @Column(name = "price_vnd", nullable = false)
    private Long priceVnd; // CHECK (>=1000 và bội số 1000) được enforce ở DB

    @Convert(converter = TemplateStatusConverter.class)
    @Column(nullable = false, length = 16)
    private TemplateStatus status = TemplateStatus.DRAFT;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "preview_page_id")
    private Page previewPage;

    @ManyToMany // bảng nối template_tag_map
    @JoinTable(name = "template_tag_map",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TemplateTag> tags;
}
