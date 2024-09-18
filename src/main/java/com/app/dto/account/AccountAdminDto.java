package com.app.dto.account;

import com.app.dto.ABasicAdminDto;
import com.app.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAdminDto extends ABasicAdminDto {
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "username")
    private String username;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @ApiModelProperty(name = "isSuperAdmin")
    private Boolean isSuperAdmin;
    @ApiModelProperty(name = "lastLogin")
    private Date lastLogin;
    @ApiModelProperty(name = "group")
    private GroupDto group;
}
