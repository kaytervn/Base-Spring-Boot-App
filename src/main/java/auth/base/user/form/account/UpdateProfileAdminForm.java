package auth.base.user.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileAdminForm {
    String password;
    @NotBlank(message = "oldPassword is required")
    @ApiModelProperty(required = true)
    String oldPassword;
    @NotBlank(message = "fullName is required")
    @ApiModelProperty(required = true)
    String fullName;
    String avatarPath;
}
