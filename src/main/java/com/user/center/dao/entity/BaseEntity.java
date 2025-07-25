package com.user.center.dao.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * No Lombok annotation
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 0 - Deleted, 1 - Not Deleted
     */
    @Column(name = "yn", nullable = false)
    private Boolean yn;

    /**
     * Created by who
     */
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    /**
     * Record created date and time
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Updated by who
     */
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    /**
     * Record last updated date and time
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Boolean getYn() {
        return yn;
    }

    public void setYn(Boolean yn) {
        this.yn = yn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
