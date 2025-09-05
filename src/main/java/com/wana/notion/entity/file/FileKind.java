package com.wana.notion.entity.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileKind {
    AVATAR("avatar"),
    BLOCK_IMAGE("block_image"),
    OTHER("other");

    private final String db;
}