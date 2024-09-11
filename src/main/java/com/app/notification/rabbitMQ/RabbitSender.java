package com.app.notification.rabbitMQ;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RabbitSender {
    RabbitTemplate template;
    RabbitAdmin rabbitAdmin;

    public void send(String message, String queueName) {
        if (StringUtils.isBlank(message)) {
            log.warn("Cannot send an empty or null message");
            return;
        }
        createQueueIfNotExist(queueName);
        template.convertAndSend(queueName, message);
        log.warn("Sent '{}' to queue: {}", message, queueName);
    }

    public void createQueueIfNotExist(String queueName) {
        if (!isQueueExist(queueName)) {
            log.warn("Creating queue: {}", queueName);
            rabbitAdmin.declareQueue(new Queue(queueName));
        }
    }

    public void removeQueue(String queueName) {
        if (isQueueExist(queueName)) {
            log.warn("Deleting queue: {}", queueName);
            rabbitAdmin.deleteQueue(queueName);
        }
    }

    private boolean isQueueExist(String queueName) {
        return rabbitAdmin.getQueueProperties(queueName) != null;
    }
}