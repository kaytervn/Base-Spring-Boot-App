package com.app.form.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UploadFileForm {
    @NotBlank(message = "type cannot be null")
    @ApiModelProperty(name = "type", required = true)
    private String type;
    @NotNull(message = "file cannot be null")
    @ApiModelProperty(name = "file", required = true)
    private MultipartFile file;
}
