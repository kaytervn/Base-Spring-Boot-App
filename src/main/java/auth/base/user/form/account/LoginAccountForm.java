package auth.base.user.form.account;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginAccountForm {
    @NotBlank(message = "username is required")
    String username;
    @NotBlank(message = "password is required")
    String password;
    @NotBlank(message = "grant_type is required")
    String grant_type;
}
