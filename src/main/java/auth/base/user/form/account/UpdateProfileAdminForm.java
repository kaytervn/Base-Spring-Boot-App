package auth.base.user.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileAdminForm {
    String password;
    @NotEmpty(message = "oldPassword is required")
    @ApiModelProperty(required = true)
    String oldPassword;
    @NotEmpty(message = "fullName is required")
    @ApiModelProperty(required = true)
    String fullName;
    String avatarPath;
}
