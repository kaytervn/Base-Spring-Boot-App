package com.app.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileAdminForm {
    @NotBlank(message = "fullName is required")
    String fullName;
    String avatar;
    @Size(min = 6, message = "oldPassword must be at least 6 characters long.")
    String oldPassword;
}
