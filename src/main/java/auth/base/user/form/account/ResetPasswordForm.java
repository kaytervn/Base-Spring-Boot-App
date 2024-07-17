package auth.base.user.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordForm {
    @NotEmpty(message = "otp cannot be null")
    @Schema(requiredMode = REQUIRED)
    String otp;
    @NotEmpty(message = "userId cannot be null")
    @Schema(requiredMode = REQUIRED)
    String userId;
    @Size(min = 6, message = "newPassword minimum 6 character.")
    @NotEmpty(message = "newPassword cannot be null")
    @Schema(requiredMode = REQUIRED)
    String newPassword;
}
