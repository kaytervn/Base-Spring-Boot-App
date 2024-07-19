package auth.base.user.form.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePermissionForm {
    @NotEmpty(message = "name is required")
    @ApiModelProperty(required = true)
    String name;
    @NotEmpty(message = "action is required")
    @ApiModelProperty(required = true)
    String action;
    @NotNull(message = "showMenu is required")
    @ApiModelProperty(required = true)
    Boolean showMenu;
    @NotEmpty(message = "description is required")
    @ApiModelProperty(required = true)
    String description;
    @NotEmpty(message = "groupName is required")
    @ApiModelProperty(required = true)
    String groupName;
    @NotEmpty(message = "permissionCode is required")
    @ApiModelProperty(required = true)
    String permissionCode;
}
