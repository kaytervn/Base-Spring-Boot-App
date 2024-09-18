package com.app.dto.permission;

import com.app.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionAdminDto extends ABasicAdminDto {
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "action")
    private String action;
    @ApiModelProperty(name = "showMenu")
    private Boolean showMenu;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "groupName")
    private String groupName;
    @ApiModelProperty(name = "permissionCode")
    private String permissionCode;
    @ApiModelProperty(name = "isSystem")
    private Boolean isSystem;
}
