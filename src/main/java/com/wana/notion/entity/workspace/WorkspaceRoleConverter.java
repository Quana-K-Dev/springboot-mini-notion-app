package com.wana.notion.entity.workspace;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WorkspaceRoleConverter implements AttributeConverter<WorkspaceRole, String> {
    @Override public String convertToDatabaseColumn(WorkspaceRole a) { return a == null ? null : a.getDb(); }
    @Override public WorkspaceRole convertToEntityAttribute(String s) {
        if (s == null) return null;
        for (var e : WorkspaceRole.values()) if (e.getDb().equals(s)) return e;
        throw new IllegalArgumentException("Unknown WorkspaceRole: " + s);
    }
}