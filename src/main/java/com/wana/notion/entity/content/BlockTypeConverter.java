package com.wana.notion.entity.content;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BlockTypeConverter implements AttributeConverter<BlockType, String> {
    @Override public String convertToDatabaseColumn(BlockType a) { return a == null ? null : a.getDb(); }
    @Override public BlockType convertToEntityAttribute(String s) {
        if (s == null) return null;
        for (var e : BlockType.values()) if (e.getDb().equals(s)) return e;
        throw new IllegalArgumentException("Unknown BlockType: " + s);
    }
}
