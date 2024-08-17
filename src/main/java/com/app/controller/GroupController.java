package com.app.controller;

import com.app.constant.ErrorCode;
import com.app.dto.ApiMessageDto;
import com.app.dto.ResponseListDto;
import com.app.dto.group.GroupAdminDto;
import com.app.form.group.CreateGroupForm;
import com.app.form.group.UpdateGroupForm;
import com.app.mapper.GroupMapper;
import com.app.model.Group;
import com.app.model.Permission;
import com.app.model.criteria.GroupCriteria;
import com.app.repository.GroupRepository;
import com.app.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/group")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class GroupController extends ABasicController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private GroupMapper groupMapper;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_V')")
    public ApiMessageDto<GroupAdminDto> get(@PathVariable("id") Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            return makeErrorResponse(ErrorCode.GROUP_ERROR_NOT_FOUND, "Not found group");
        }
        return makeSuccessResponse(groupMapper.fromEntityToGroupAdminDto(group), "Get group success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_L')")
    public ApiMessageDto<ResponseListDto<List<GroupAdminDto>>> list(GroupCriteria groupCriteria, Pageable pageable) {
        Page<Group> groups = groupRepository.findAll(groupCriteria.getCriteria(), pageable);
        ResponseListDto<List<GroupAdminDto>> responseListObj = new ResponseListDto<>();
        responseListObj.setContent(groupMapper.fromEntityListToGroupAdminDtoList(groups.getContent()));
        responseListObj.setTotalPages(groups.getTotalPages());
        responseListObj.setTotalElements(groups.getTotalElements());
        return makeSuccessResponse(responseListObj, "Get list group success");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateGroupForm createGroupForm, BindingResult bindingResult) {
        Group groupByName = groupRepository.findFirstByName(createGroupForm.getName()).orElse(null);
        if (groupByName != null) {
            return makeErrorResponse(ErrorCode.GROUP_ERROR_NAME_EXISTED, "Name existed");
        }
        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < createGroupForm.getPermissions().size(); i++) {
            permissionRepository.findById(createGroupForm.getPermissions().get(i)).ifPresent(permissions::add);
        }
        Group group = groupMapper.fromCreateGroupFormToEntity(createGroupForm);
        group.setPermissions(permissions);
        groupRepository.save(group);
        return makeSuccessResponse(null, "Create group success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GR_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateGroupForm updateGroupForm, BindingResult bindingResult) {
        Group group = groupRepository.findById(updateGroupForm.getId()).orElse(null);
        if (group == null) {
            return makeErrorResponse(ErrorCode.GROUP_ERROR_NOT_FOUND, "Not found group");
        }
        if (!group.getName().equals(updateGroupForm.getName())) {
            Group groupByName = groupRepository.findFirstByName(updateGroupForm.getName()).orElse(null);
            if (groupByName != null) {
                return makeErrorResponse(ErrorCode.GROUP_ERROR_NAME_EXISTED, "Name existed");
            }
        }
        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < updateGroupForm.getPermissions().size(); i++) {
            Permission permission = permissionRepository.findById(updateGroupForm.getPermissions().get(i)).orElse(null);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        groupMapper.fromUpdateGroupFormToEntity(updateGroupForm, group);
        group.setPermissions(permissions);
        groupRepository.save(group);
        return makeSuccessResponse(null, "Update group success");
    }
}
