package auth.base.user.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginAccountForm {
    @NotEmpty(message = "username is required")
    @ApiModelProperty(required = true)
    String username;
    @NotEmpty(message = "password is required")
    @ApiModelProperty(required = true)
    String password;
    @NotEmpty(message = "grant_type is required")
    @ApiModelProperty(required = true)
    String grant_type;
}
