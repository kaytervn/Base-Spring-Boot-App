package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "db_app_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Account extends Auditable<String> {
    Integer kind;
    @Column(unique = true)
    String username;
    @Column(unique = true)
    String phone;
    @Column(unique = true)
    String email;
    @JsonIgnore
    String password;
    String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    Group group;
    Date lastLogin;
    String avatar;
    String resetPasswordCode;
    Date resetPasswordTime;
    @Column(name = "attempt_forget_password")
    Integer attemptCode;
    Integer attemptLogin;
    Boolean isSuperAdmin = false;
}
