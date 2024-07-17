package auth.base.user.dto.account;

import auth.base.user.dto.ABasicAdminDto;
import auth.base.user.dto.group.GroupDto;
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
    Date lastLogin;
    String avatar;
    Boolean isSuperAdmin;
    GroupDto group;
}
