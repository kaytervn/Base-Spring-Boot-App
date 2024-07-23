package auth.base.user.exception;

import auth.base.user.dto.ApiMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController {
    @GetMapping
    public ResponseEntity<ApiMessageDto<String>> error() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiMessageDto<>());
    }
}
