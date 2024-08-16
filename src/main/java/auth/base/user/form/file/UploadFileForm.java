package auth.base.user.form.file;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileForm {
    @NotBlank(message = "type is required")
    String type;
    @NotNull(message = "file is required")
    MultipartFile file;
}
