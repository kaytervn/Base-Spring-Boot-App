package auth.base.user.form.group;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGroupForm {
    @NotNull(message = "id is required")
    Long id;
    @NotBlank(message = "name is required")
    String name;
    @NotBlank(message = "description is required")
    String description;
    @NotNull(message = "permissions is required")
    List<Long> permissions;
}
