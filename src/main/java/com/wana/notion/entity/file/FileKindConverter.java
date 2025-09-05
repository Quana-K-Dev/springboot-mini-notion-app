package com.wana.notion.entity.file;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FileKindConverter implements AttributeConverter<FileKind, String> {
    @Override public String convertToDatabaseColumn(FileKind a) { return a == null ? null : a.getDb(); }
    @Override public FileKind convertToEntityAttribute(String s) {
        if (s == null) return null;
        for (var e : FileKind.values()) if (e.getDb().equals(s)) return e;
        throw new IllegalArgumentException("Unknown FileKind: " + s);
    }
}
