package com.app.controller;

import com.app.constant.ErrorCode;
import com.app.dto.ApiMessageDto;
import com.app.form.permission.CreatePermissionForm;
import com.app.mapper.PermissionMapper;
import com.app.model.Permission;
import com.app.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class PermissionController extends ABasicController {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_L')")
    public ApiMessageDto<List<Permission>> list() {
        ApiMessageDto<List<Permission>> apiMessageDto = new ApiMessageDto<>();
        Page<Permission> accounts = permissionRepository.findAll(PageRequest.of(0, 1000, Sort.by(new Sort.Order(Sort.Direction.DESC, "createdDate"))));
        apiMessageDto.setData(accounts.getContent());
        apiMessageDto.setMessage("Get permissions list success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePermissionForm createPermissionForm, BindingResult bindingResult) {
        Permission permissionByName = permissionRepository.findFirstByName(createPermissionForm.getName());
        if (permissionByName != null) {
            return makeErrorResponse(ErrorCode.PERMISSION_ERROR_NAME_EXISTED, "Name existed");
        }
        Permission permission = permissionMapper.fromCreatePermissionFormToEntity(createPermissionForm);
        permissionRepository.save(permission);
        return makeSuccessResponse(null, "Create permission success");
    }
}