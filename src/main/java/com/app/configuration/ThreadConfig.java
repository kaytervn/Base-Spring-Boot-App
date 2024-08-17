package com.app.configuration;

import com.app.constant.AppConstant;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadConfig {
    @Value("${thread.pool.size:10}")
    int threadPoolSize;
    @Value("${thread.pool.queue.size:150}")
    int threadQueuePoolSize;

    @Bean(name = AppConstant.APP_THREAD_POOL_EXECUTOR)
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolSize);
        executor.setMaxPoolSize(threadPoolSize);
        executor.setQueueCapacity(threadQueuePoolSize);
        executor.setThreadNamePrefix("spring-app-invoke-");
        executor.initialize();
        return executor;
    }
}

