package com.app.model;

import com.app.constant.AppConstant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_app_group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends Auditable<String> {
    @Column(unique = true)
    String name;
    @Column(columnDefinition = "text")
    String description;
    Integer kind;
    Boolean isSystemRole = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "db_app_permission_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",
                    referencedColumnName = "id"))
    List<Permission> permissions = new ArrayList<>();
}
