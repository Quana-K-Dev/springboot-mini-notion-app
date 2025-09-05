package com.wana.notion.entity.common;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@MappedSuperclass // dùng cho các bảng CHỈ có created_at (không có updated_at trong schema)
public abstract class BaseCreatedEntity {
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;
}