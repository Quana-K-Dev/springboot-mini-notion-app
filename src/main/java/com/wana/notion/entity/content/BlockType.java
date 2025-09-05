package com.wana.notion.entity.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlockType {
    PARAGRAPH("paragraph"),
    HEADING("heading"),
    LIST("list"),
    TODO("todo"),
    QUOTE("quote"),
    CODE("code"),
    IMAGE("image"),
    DIVIDER("divider"),
    CALLOUT("callout"),
    TABLE("table");

    private final String db;
}