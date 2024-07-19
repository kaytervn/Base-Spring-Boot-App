package auth.base.user.form.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGroupForm {
    @NotEmpty(message = "name is required")
    @ApiModelProperty(required = true)
    String name;
    @NotNull(message = "kind is required")
    @ApiModelProperty(required = true)
    Integer kind;
    @NotEmpty(message = "description is required")
    @ApiModelProperty(required = true)
    String description;
    @NotNull(message = "permissions is required")
    @ApiModelProperty(required = true)
    Long[] permissions;
}
