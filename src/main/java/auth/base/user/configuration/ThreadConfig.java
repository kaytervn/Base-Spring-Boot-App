package auth.base.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadConfig {

    @Value("${thread.pool.size:10}")
    private int threadPoolSize;

    @Value("${thread.pool.queue.size:150}")
    private int threadQueuePoolSize;

    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolSize);
        executor.setMaxPoolSize(threadPoolSize);
        executor.setQueueCapacity(threadQueuePoolSize);
        executor.setThreadNamePrefix("app-auth-invoke-");
        executor.initialize();
        return executor;
    }
}

