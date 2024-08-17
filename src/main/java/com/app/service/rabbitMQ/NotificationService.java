package com.app.service.rabbitMQ;

import com.app.constant.AppConstant;
import com.app.form.BaseMessageForm;
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
    @Value("${rabbitmq.notification.queue}")
    String queueName;

    private <T> void sendMessage(T data, String cmd) {
        BaseMessageForm<T> form = new BaseMessageForm<>();
        form.setApp(AppConstant.BACKEND_APP);
        form.setCommand(cmd);
        form.setData(data);
        try {
            String msg = objectMapper.writeValueAsString(form);
            rabbitMQService.createQueueIfNotExist(queueName);
            rabbitSender.send(msg, queueName);
        } catch (Exception e) {
            log.error("Error sending notification: ", e);
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}

