package com.app.rabbitMQ.form;

import lombok.Data;

@Data
public class BaseMessageForm<T> {
    private String app;
    private String command;
    private String token;
    private T data;
}
