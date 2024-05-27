package com.cts.ecart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(name="created_at", updatable = false)
    private Date createdAt;

    @CreatedBy
    @Column(name="created_by", updatable = false, columnDefinition = "varchar(100) default 'SYSTEM'")
    private String createdBy;

    @LastModifiedDate
    @Column(name="updated_at", insertable = false)
    private Date updatedAt;

    @Column(name="updated_by", insertable = false, columnDefinition = "varchar(100) default 'SYSTEM'")
    private String updatedBy;
}
