package com.app.multitenancy.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DbConfigDto {
    Long id;
    String name;
    String url;
    String username;
    String password;
    String driverClassName;
    Boolean initialize;
    Integer updateStatus;
    Integer maxConnection;
}
