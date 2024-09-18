package com.app.dto.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UploadFileDto {
    @ApiModelProperty(name = "filePath")
    private String filePath;
}
