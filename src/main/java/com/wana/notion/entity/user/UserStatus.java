package com.wana.notion.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("active"),
    BLOCKED("blocked"),
    PENDING_VERIFICATION("pending_verification"); // khớp ENUM trong SQL

    private final String db; // giá trị thực lưu trong DB (lowercase)
}