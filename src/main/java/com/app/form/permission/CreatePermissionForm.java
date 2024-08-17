package com.app.form.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePermissionForm {
    @NotBlank(message = "name is required")
    String name;
    @NotBlank(message = "action is required")
    String action;
    @NotNull(message = "showMenu is required")
    Boolean showMenu;
    @NotBlank(message = "description is required")
    String description;
    @NotBlank(message = "groupName is required")
    String groupName;
    @NotBlank(message = "permissionCode is required")
    String permissionCode;
}
