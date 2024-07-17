package auth.base.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountForTokenDto {
    Long id;
    Integer kind;
    String username;
    String email;
    String fullName;
    Boolean isSuperAdmin;
}
