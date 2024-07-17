package auth.base.user.dto.permission;

import auth.base.user.dto.ABasicAdminDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionAdminDto extends ABasicAdminDto {
    String name;
    String action;
    Boolean showMenu;
    String description;
    String groupName;
    String permissionCode;
}
