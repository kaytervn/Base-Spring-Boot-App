package com.app.form.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreatePermissionForm {
    @NotBlank(message = "name cannot be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @NotBlank(message = "action cannot be null")
    @ApiModelProperty(name = "action", required = true)
    private String action;
    @NotNull(message = "showMenu cannot be null")
    @ApiModelProperty(name = "showMenu", required = true)
    private Boolean showMenu;
    @NotBlank(message = "description cannot be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @NotBlank(message = "groupName cannot be null")
    @ApiModelProperty(name = "groupName", required = true)
    private String groupName;
    @NotBlank(message = "permissionCode cannot be null")
    @ApiModelProperty(name = "permissionCode", required = true)
    private String permissionCode;
    @NotNull(message = "isSystem cannot be null")
    @ApiModelProperty(name = "isSystem", required = true)
    private Boolean isSystem;
}
