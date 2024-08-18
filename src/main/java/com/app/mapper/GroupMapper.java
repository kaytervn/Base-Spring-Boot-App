package com.app.mapper;

import com.app.dto.group.GroupAdminDto;
import com.app.dto.group.GroupDto;
import com.app.form.group.CreateGroupForm;
import com.app.form.group.UpdateGroupForm;
import com.app.model.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PermissionMapper.class})
public interface GroupMapper extends ABasicMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    Group fromCreateGroupFormToEntity(CreateGroupForm createGroupForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateGroupFormToEntity(UpdateGroupForm updateGroupForm, @MappingTarget Group group);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "permissions", target = "permissions", qualifiedByName = "fromEntityListToPermissionDtoList")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupAdminDto")
    GroupAdminDto fromEntityToGroupAdminDto(Group group);

    @IterableMapping(elementTargetType = GroupAdminDto.class, qualifiedByName = "fromEntityToGroupAdminDto")
    List<GroupAdminDto> fromEntityListToGroupAdminDtoList(List<Group> groups);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupDto")
    GroupDto fromEntityToGroupDto(Group group);

    @IterableMapping(elementTargetType = GroupDto.class, qualifiedByName = "fromEntityToGroupDto")
    List<GroupDto> fromEntityListToGroupDtoList(List<Group> groups);
}
