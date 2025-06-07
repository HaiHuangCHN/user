package com.user.center.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO hai 这个是干啥用的？
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

}