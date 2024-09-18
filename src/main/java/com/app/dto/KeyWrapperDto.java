package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyWrapperDto {
    private String decryptKey;
    private String encryptKey;
}

