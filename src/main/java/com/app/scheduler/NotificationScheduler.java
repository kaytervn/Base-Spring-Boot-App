package com.app.scheduler;

import com.app.rabbitMQ.constant.RabbitMQConstant;
import com.app.rabbitMQ.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationScheduler {
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC") // Cron format: second | minute | hour | day of month | month | day of week
    public void sendNotification() {
        notificationService.sendMessage("Hello World!", RabbitMQConstant.POST_NOTIFICATION_COMMAND);
    }
}
