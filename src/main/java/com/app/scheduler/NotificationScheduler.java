package com.app.scheduler;

import com.app.constant.AppConstant;
import com.app.service.rabbitMQ.NotificationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationScheduler {
    @Autowired
    NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    // cron format: second | minute | hour | day of month | month | day of week
    public void sendNotification() {
        notificationService.sendMessage("Test Message", AppConstant.BACKEND_POST_NOTIFICATION_COMMAND);
    }
}
