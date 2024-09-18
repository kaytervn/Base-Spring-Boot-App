package com.app.model;

import com.app.constant.AppConstant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable<T> extends ReuseId {
    private Integer status = 1;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private T createdBy;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;
    @LastModifiedBy
    @Column(nullable = false)
    private T modifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    private Date modifiedDate;
}
