package com.wana.notion.dto;

import com.wana.notion.entity.user.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class AdminUserDto {
    private Long id;
    private String email;
    private String displayName;
    private String avatarUrl;
    private UserStatus status;
    private Instant createdAt;
    private Instant lastLoginAt;
    private List<String> roles;
    private boolean isAdmin;
    private boolean isSeller;
}
