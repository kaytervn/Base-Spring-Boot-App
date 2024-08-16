package auth.base.user.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountAdminForm {
    @NotNull(message = "id is required")
    @ApiModelProperty(required = true)
    Long id;
    String password;
    @NotBlank(message = "fullName is required")
    @ApiModelProperty(required = true)
    String fullName;
    String avatarPath;
    String email;
    String phone;
    @NotNull(message = "status is required")
    @ApiModelProperty(required = true)
    Integer status;
    @NotNull(message = "groupId is required")
    @ApiModelProperty(required = true)
    Long groupId;
}
