package com.app.dto.account;

import com.app.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountDto {
    @ApiModelProperty(name = "id")
    private Long id;
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
    @ApiModelProperty(name = "group")
    private GroupDto group;
}
