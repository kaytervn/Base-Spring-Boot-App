package com.app.dto.account;

import com.app.dto.ABasicAdminDto;
import com.app.dto.group.GroupDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountAdminDto extends ABasicAdminDto {
    Integer kind;
    String username;
    String phone;
    String email;
    String fullName;
    String avatar;
    Boolean isSuperAdmin;
    Date lastLogin;
    GroupDto group;
}
