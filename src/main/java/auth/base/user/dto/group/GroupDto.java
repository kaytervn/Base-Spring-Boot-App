package auth.base.user.dto.group;

import auth.base.user.dto.permission.PermissionDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDto {
    Long id;
    String name;
    Integer kind;
    List<PermissionDto> permissions;
}
