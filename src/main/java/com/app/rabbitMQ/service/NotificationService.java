package com.app.rabbitMQ.service;

import com.app.rabbitMQ.constant.RabbitMQConstant;
import com.app.rabbitMQ.form.BaseMessageForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private RabbitMQService rabbitMQService;
    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    public <T> void sendMessage(T data, String cmd) {
        BaseMessageForm<T> form = new BaseMessageForm<>();
        form.setApp(RabbitMQConstant.SPRING_APP);
        form.setCommand(cmd);
        form.setData(data);
        try {
            String msg = objectMapper.writeValueAsString(form);
            rabbitMQService.createQueueIfNotExist(queueName);
            rabbitSender.send(msg, queueName);
        } catch (Exception e) {
            log.error("Error sending service: ", e);
            throw new RuntimeException("Failed to send service", e);
        }
    }
}

