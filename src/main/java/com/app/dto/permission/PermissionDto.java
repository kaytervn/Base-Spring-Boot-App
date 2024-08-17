package com.app.dto.permission;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionDto {
    Long id;
    String name;
    String action;
    String groupName;
    String permissionCode;
}
