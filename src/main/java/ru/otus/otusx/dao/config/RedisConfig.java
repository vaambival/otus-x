package ru.otus.otusx.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.otus.otusx.dao.entity.Post;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Post> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Post> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
