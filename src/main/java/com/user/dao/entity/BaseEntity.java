package com.user.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "createdAt cannot be null")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @NotNull(message = "updatedAt cannot be null")
    private Timestamp updatedAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BaseEntity [createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

}
