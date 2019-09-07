package com.joker.common;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Joker Jing
 */
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
 
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
 
}