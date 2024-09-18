package com.app.multitenancy.dto;

import lombok.Data;

@Data
public class DbConfigDto {
    private Long id;
    private String name;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Boolean initialize;
    private Integer updateStatus;
    private Integer maxConnection;
    private String license;
}