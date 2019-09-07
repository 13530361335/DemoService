package com.joker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Joker Jing
 */
@Configuration
public class RabbitConfig {
 
    @Bean
    public Queue queue() {
        return new Queue("hello");
    }
 
}