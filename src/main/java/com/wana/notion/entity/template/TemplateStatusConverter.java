package com.wana.notion.entity.template;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TemplateStatusConverter implements AttributeConverter<TemplateStatus, String> {
    @Override public String convertToDatabaseColumn(TemplateStatus a) { return a == null ? null : a.getDb(); }
    @Override public TemplateStatus convertToEntityAttribute(String s) {
        if (s == null) return null;
        for (var e : TemplateStatus.values()) if (e.getDb().equals(s)) return e;
        throw new IllegalArgumentException("Unknown TemplateStatus: " + s);
    }
}
