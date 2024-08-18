package com.app.form.group;

import com.app.validation.GroupKind;
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
    @GroupKind
    Integer kind;
    @NotBlank(message = "description is required")
    String description;
    @NotNull(message = "permissions is required")
    List<Long> permissions;
}
