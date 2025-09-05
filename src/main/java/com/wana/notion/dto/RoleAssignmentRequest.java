package com.wana.notion.dto;

import lombok.Data;

@Data
public class RoleAssignmentRequest {
    private Long userId;
    private String action; // "GRANT" or "REVOKE"
    private String roleCode; // "ROLE_ADMIN" or "ROLE_SELLER"
}
