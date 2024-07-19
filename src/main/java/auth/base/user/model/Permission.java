package auth.base.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_base_permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends Auditable<String> {
    @Column(unique = true)
    String name;
    String action;
    Boolean showMenu;
    @Column(columnDefinition = "text")
    String description;
    String groupName;
    @Column(unique = true)
    String permissionCode;
}
