package com.app.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BadRequestException extends RuntimeException {
    String code;

    public BadRequestException(String message) {
        super(message);
        this.code = null;
    }

    public BadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}