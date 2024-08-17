package com.app.dto.group;

import com.app.dto.ABasicAdminDto;
import com.app.dto.permission.PermissionDto;
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
