package com.wana.notion.entity.rbac;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable // khóa chính tổng hợp (user_id, role_id)
public class UserRoleId implements Serializable {
    private Long userId;
    private Long roleId;
}