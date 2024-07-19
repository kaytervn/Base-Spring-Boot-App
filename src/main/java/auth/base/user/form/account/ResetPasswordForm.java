package auth.base.user.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordForm {
    @NotEmpty(message = "otp is required")
    @ApiModelProperty(required = true)
    String otp;
    @NotEmpty(message = "userId is required")
    @ApiModelProperty(required = true)
    String userId;
    @Size(min = 6, message = "newPassword minimum 6 character")
    @NotEmpty(message = "newPassword is required")
    @ApiModelProperty(required = true)
    String newPassword;
}
