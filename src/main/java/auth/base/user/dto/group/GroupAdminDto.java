package auth.base.user.dto.group;

import auth.base.user.dto.ABasicAdminDto;
import auth.base.user.dto.permission.PermissionDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupAdminDto extends ABasicAdminDto {
    String name;
    String description;
    Integer kind;
    List<PermissionDto> permissions;
}
