package ru.otus.otusx.logic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Slf4j
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("NewsfeedCacheAsyncThread-");
        executor.setRejectedExecutionHandler((r, executor1) ->
                log.warn("Task rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }
}
