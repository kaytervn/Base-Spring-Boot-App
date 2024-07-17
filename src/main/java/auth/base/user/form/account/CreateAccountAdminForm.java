package auth.base.user.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountAdminForm {
    @NotEmpty(message = "username cannot be null")
    @Schema(requiredMode = REQUIRED)
    String username;
    @NotEmpty(message = "password cannot be null")
    @Schema(requiredMode = REQUIRED)
    String password;
    @NotEmpty(message = "fullName cannot be null")
    @Schema(requiredMode = REQUIRED)
    String fullName;
    String avatarPath;
    @Email
    @NotEmpty(message = "email cannot be null")
    @Schema(requiredMode = REQUIRED)
    String email;
    String phone;
    @NotNull(message = "kind cannot be null")
    @Schema(requiredMode = REQUIRED)
    Integer kind;
    @NotNull(message = "status cannot be null")
    @Schema(requiredMode = REQUIRED)
    Integer status;
    @NotNull(message = "groupId cannot be null")
    @Schema(requiredMode = REQUIRED)
    Long groupId;
}
