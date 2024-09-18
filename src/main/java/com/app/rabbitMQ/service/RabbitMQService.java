package com.app.rabbitMQ.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQService {
    @Autowired
    private RabbitSender rabbitSender;

    public void createQueueIfNotExist(String queueName) {
        rabbitSender.createQueueIfNotExist(queueName);
    }
}

