package auth.base.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_base_group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends Auditable<String> {
    @Column(unique = true)
    String name;
    @Column(length = 1000)
    String description;
    Integer kind;
    Boolean isSystemRole = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "db_base_permission_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",
                    referencedColumnName = "id"))
    List<Permission> permissions = new ArrayList<>();
}
