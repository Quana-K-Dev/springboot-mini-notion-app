package com.wana.notion.entity.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // tự động áp dụng cho các field kiểu UserStatus
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute == null ? null : attribute.getDb();
    }
    @Override public UserStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (var e : UserStatus.values()) if (e.getDb().equals(dbData)) return e;
        throw new IllegalArgumentException("Unknown UserStatus: " + dbData);
    }
}