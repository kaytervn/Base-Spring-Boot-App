package com.app.dto.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "action")
    private String action;
    @ApiModelProperty(name = "groupName")
    private String groupName;
    @ApiModelProperty(name = "permissionCode")
    private String permissionCode;
    @ApiModelProperty(name = "isSystem")
    private Boolean isSystem;
}
