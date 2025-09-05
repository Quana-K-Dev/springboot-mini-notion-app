package com.wana.notion.entity.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateStatus {
    DRAFT("draft"),
    PENDING("pending"),
    APPROVED("approved"),
    REMOVED("removed");

    private final String db;
}
