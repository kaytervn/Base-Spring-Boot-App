package com.app.mapper;

import com.app.dto.permission.PermissionAdminDto;
import com.app.dto.permission.PermissionDto;
import com.app.form.permission.CreatePermissionForm;
import com.app.model.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper extends ABasicMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "permissionCode", target = "permissionCode")
    @BeanMapping(ignoreByDefault = true)
    Permission fromCreatePermissionFormToEntity(CreatePermissionForm createPermissionForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "permissionCode", target = "permissionCode")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionAdminDto")
    PermissionAdminDto fromEntityToPermissionAdminDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionAdminDto.class, qualifiedByName = "fromEntityToPermissionAdminDto")
    List<PermissionAdminDto> fromEntityListToPermissionAdminDtoList(List<Permission> permissions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "permissionCode", target = "permissionCode")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionDto")
    PermissionDto fromEntityToPermissionDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionDto.class, qualifiedByName = "fromEntityToPermissionDto")
    List<PermissionDto> fromEntityListToPermissionDtoList(List<Permission> permissions);
}
