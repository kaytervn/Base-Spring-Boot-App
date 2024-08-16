package auth.base.user.dto.account;

import auth.base.user.dto.group.GroupDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    Long id;
    Integer kind;
    String username;
    String phone;
    String email;
    String fullName;
    String avatarPath;
    GroupDto group;
}
