package com.app.form.setting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateSettingForm {
    @NotNull(message = "id cannot be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotBlank(message = "valueData cannot be null")
    @ApiModelProperty(name = "valueData", required = true)
    private String valueData;
    @NotNull(message = "status cannot be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
    @NotBlank(message = "groupName cannot be null")
    @ApiModelProperty(name = "groupName", required = true)
    private String groupName;
    @NotBlank(message = "keyName cannot be null")
    @ApiModelProperty(name = "keyName", required = true)
    private String keyName;
    @NotBlank(message = "dataType cannot be null")
    @ApiModelProperty(name = "dataType", required = true)
    private String dataType;
    @NotNull(message = "isSystem cannot be null")
    @ApiModelProperty(name = "isSystem", required = true)
    private Boolean isSystem;
}
