package com.wana.notion.entity.workspace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkspaceRole {
    OWNER("owner"),
    EDITOR("editor"),
    VIEWER("viewer");

    private final String db;
}