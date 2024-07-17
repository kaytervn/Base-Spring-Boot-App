package auth.base.user.form.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginAccountForm {
    @NotEmpty(message = "username cannot be null")
    @Schema(requiredMode = REQUIRED)
    String username;
    @NotEmpty(message = "password cannot be null")
    @Schema(requiredMode = REQUIRED)
    String password;
    @NotEmpty(message = "grant_type cannot be null")
    @Schema(requiredMode = REQUIRED)
    String grant_type;
}
