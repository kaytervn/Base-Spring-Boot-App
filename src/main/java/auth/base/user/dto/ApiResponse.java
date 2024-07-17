package auth.base.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    Boolean result = true;
    String code;
    Integer httpCode;
    String message;
    T data;

    public ApiResponse(HttpStatus httpCode, String message, T result) {
        this.httpCode = httpCode.value();
        this.message = message;
        this.data = result;
    }

    public ApiResponse(HttpStatus httpCode, String message) {
        this.httpCode = httpCode.value();
        this.message = message;
    }
}
