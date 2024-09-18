package com.app.model;

import com.app.constant.AppConstant;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "db_app_permission")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Auditable<String> {
    @Id
    @GeneratedValue(generator = AppConstant.APP_ID_GENERATOR_NAME)
    @GenericGenerator(name = AppConstant.APP_ID_GENERATOR_NAME, strategy = AppConstant.APP_ID_GENERATOR_STRATEGY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String action;
    private Boolean showMenu;
    @Column(columnDefinition = "text")
    private String description;
    private String groupName;
    @Column(unique = true)
    private String permissionCode;
    private Boolean isSystem;
}
