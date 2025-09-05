package com.wana.notion.entity.template;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity @Table(name = "template_tags",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_template_tags_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_template_tags_slug", columnNames = "slug")
        })
public class TemplateTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 64)
    private String slug;
}