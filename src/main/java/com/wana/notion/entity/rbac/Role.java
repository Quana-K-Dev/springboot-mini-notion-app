package com.wana.notion.entity.rbac;

import com.wana.notion.entity.common.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // chỉ dựa vào id để tránh vòng lặp/equality nặng
@ToString
@Entity @Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(name = "uk_roles_code", columnNames = "code"))
public class Role extends BaseCreatedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 50) // ROLE_USER, ROLE_ADMIN, ROLE_SELLER
    private String code;

    @Column(length = 100)
    private String name;
}
