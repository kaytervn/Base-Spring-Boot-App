package com.app.model;

import com.app.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "db_app_account")
@EntityListeners(AuditingEntityListener.class)
public class Account extends Auditable<String> {
    @Id
    @GeneratedValue(generator = AppConstant.APP_ID_GENERATOR_NAME)
    @GenericGenerator(name = AppConstant.APP_ID_GENERATOR_NAME, strategy = AppConstant.APP_ID_GENERATOR_STRATEGY)
    private Long id;
    private Integer kind;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    private Date lastLogin;
    private String avatar;
    private String resetPasswordCode;
    private Date resetPasswordTime;
    @Column(name = "attempt_forget_password")
    private Integer attemptCode;
    private Integer attemptLogin;
    private Boolean isSuperAdmin = false;
}
