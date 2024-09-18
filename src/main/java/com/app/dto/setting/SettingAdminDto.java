package com.app.dto.setting;

import com.app.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SettingAdminDto extends ABasicAdminDto {
    @ApiModelProperty(name = "groupName")
    private String groupName;
    @ApiModelProperty(name = "keyName")
    private String keyName;
    @ApiModelProperty(name = "valueData")
    private String valueData;
    @ApiModelProperty(name = "dataType")
    private String dataType;
    @ApiModelProperty(name = "isSystem")
    private Boolean isSystem;
}
