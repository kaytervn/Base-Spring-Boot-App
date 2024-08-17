package com.app.dto.group;

import com.app.dto.permission.PermissionDto;
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
