package auth.base.user.form.group;

import auth.base.user.validation.GroupKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGroupForm {
    @NotBlank(message = "name is required")
    String name;
    @NotNull(message = "kind is required")
    @GroupKind
    Integer kind;
    @NotBlank(message = "description is required")
    String description;
    @NotNull(message = "permissions is required")
    List<Long> permissions;
}
