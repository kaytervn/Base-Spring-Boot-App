package auth.base.user.form.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileForm {
    @NotEmpty(message = "type is required")
    @Schema(requiredMode = REQUIRED)
    String type;
    @NotNull(message = "file is required")
    @Schema(requiredMode = REQUIRED)
    MultipartFile file;
}
