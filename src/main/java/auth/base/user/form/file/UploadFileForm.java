package auth.base.user.form.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileForm {
    @NotEmpty(message = "type is required")
    @ApiModelProperty(required = true)
    String type;
    @NotNull(message = "file is required")
    @ApiModelProperty(required = true)
    MultipartFile file;
}
