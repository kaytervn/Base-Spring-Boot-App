package auth.base.user.form.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGroupForm {
    @NotEmpty(message = "Name cannot be null")
    @Schema(requiredMode = REQUIRED)
    String name;
    @NotEmpty(message = "description cannot be null")
    @Schema(requiredMode = REQUIRED)
    String description;
    @NotNull(message = "permissions cannot be null")
    @Schema(requiredMode = REQUIRED)
    Long[] permissions;
    @NotNull(message = "kind cannot be null")
    @Schema(requiredMode = REQUIRED)
    Integer kind;
}
