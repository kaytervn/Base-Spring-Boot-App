package com.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean result = true;
    private String code;
    private Integer httpCode;
    private String message;
    private T data;

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
