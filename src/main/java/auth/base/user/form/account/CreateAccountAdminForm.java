package auth.base.user.form.account;

import auth.base.user.validation.AccountKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountAdminForm {
    @NotEmpty(message = "username is required")
    @ApiModelProperty(required = true)
    String username;
    @NotEmpty(message = "password is required")
    @ApiModelProperty(required = true)
    String password;
    @NotEmpty(message = "fullName is required")
    @ApiModelProperty(required = true)
    String fullName;
    String avatarPath;
    @Email
    @NotEmpty(message = "email is required")
    @ApiModelProperty(required = true)
    String email;
    String phone;
    @NotNull(message = "kind is required")
    @AccountKind
    @ApiModelProperty(required = true)
    Integer kind;
    @NotNull(message = "status is required")
    @ApiModelProperty(required = true)
    Integer status;
    @NotNull(message = "groupId is required")
    @ApiModelProperty(required = true)
    Long groupId;
}
