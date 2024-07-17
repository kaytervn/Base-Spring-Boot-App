package auth.base.user.form.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePermissionForm {
    @NotEmpty(message = "name cannot be null")
    @Schema(requiredMode = REQUIRED)
    String name;
    @NotEmpty(message = "action cannot be null")
    @Schema(requiredMode = REQUIRED)
    String action;
    @NotNull(message = "showMenu cannot be null")
    @Schema(requiredMode = REQUIRED)
    Boolean showMenu;
    @NotEmpty(message = "description cannot be null")
    @Schema(requiredMode = REQUIRED)
    String description;
    @NotEmpty(message = "groupName cannot be null")
    @Schema(requiredMode = REQUIRED)
    String groupName;
    @NotEmpty(message = "permissionCode cannot be null")
    @Schema(requiredMode = REQUIRED)
    String permissionCode;
}
