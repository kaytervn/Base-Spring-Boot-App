package auth.base.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String description;
    String groupName;
    String permissionCode;
}
