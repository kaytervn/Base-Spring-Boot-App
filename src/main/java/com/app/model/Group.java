package com.app.model;

import com.app.constant.AppConstant;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "db_app_group")
@EntityListeners(AuditingEntityListener.class)
public class Group extends Auditable<String> {
    @Id
    @GeneratedValue(generator = AppConstant.APP_ID_GENERATOR_NAME)
    @GenericGenerator(name = AppConstant.APP_ID_GENERATOR_NAME, strategy = AppConstant.APP_ID_GENERATOR_STRATEGY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private Integer kind;
    private Boolean isSystemRole = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "db_app_permission_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",
                    referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();
}
