package com.app.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "db_app_permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Auditable<String> {
    @Column(unique = true)
    String name;
    @Column(unique = true)
    String action;
    Boolean showMenu;
    @Column(columnDefinition = "text")
    String description;
    String groupName;
    @Column(unique = true)
    String permissionCode;
}
