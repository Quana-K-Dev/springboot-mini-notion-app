package com.wana.notion.entity.common;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass // lớp cơ sở không tạo bảng riêng, chỉ "kế thừa cột" cho entity con
public abstract class BaseCreatedUpdatedEntity {
    @CreationTimestamp // Hibernate tự set khi insert
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @UpdateTimestamp // Hibernate tự set khi update
    @Column(name = "updated_at", nullable = false)
    protected Instant updatedAt;
}