package com.app.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiMessageDto<T> {
    Boolean result = true;
    String code = null;
    T data = null;
    String message = null;
    String firebaseUrl;
    String urlBase;
}
