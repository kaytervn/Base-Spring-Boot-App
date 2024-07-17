package auth.base.user.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileAdminForm {
    String password;
    @NotEmpty(message = "oldPassword cannot be null")
    @Schema(requiredMode = REQUIRED)
    String oldPassword;
    @NotEmpty(message = "fullName cannot be null")
    @Schema(requiredMode = REQUIRED)
    String fullName;
    String avatarPath;
}
