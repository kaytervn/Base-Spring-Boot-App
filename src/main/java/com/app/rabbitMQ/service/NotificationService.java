package com.app.rabbitMQ.service;

import com.app.rabbitMQ.constant.RabbitMQConstant;
import com.app.rabbitMQ.form.BaseMessageForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    RabbitMQService rabbitMQService;
    RabbitSender rabbitSender;
    ObjectMapper objectMapper;
    @NonFinal
    @Value("${rabbitmq.queue.name}")
    String queueName;

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

