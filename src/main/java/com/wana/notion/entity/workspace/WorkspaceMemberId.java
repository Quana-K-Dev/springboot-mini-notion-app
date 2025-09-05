package com.wana.notion.entity.workspace;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable // khóa chính tổng hợp (workspace_id, user_id)
public class WorkspaceMemberId implements Serializable {
    private Long workspaceId;
    private Long userId;
}