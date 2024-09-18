package com.app.form.group;

import com.app.validation.GroupKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateGroupForm {
    @NotBlank(message = "name cannot be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @GroupKind
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;
    @NotBlank(message = "description cannot be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @NotNull(message = "permissions cannot be null")
    @ApiModelProperty(name = "permissions", required = true)
    private List<Long> permissions;
}
