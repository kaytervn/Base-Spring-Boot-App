package auth.base.user.form.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGroupForm {
    @NotNull(message = "id cannot be null")
    @Schema(requiredMode = REQUIRED)
    Long id;
    @NotNull(message = "name cannot be null")
    @Schema(requiredMode = REQUIRED)
    String name;
    @Schema(requiredMode = REQUIRED)
    String description;
    @NotNull(message = "permissions cannot be null")
    @Schema(requiredMode = REQUIRED)
    Long[] permissions;
}
