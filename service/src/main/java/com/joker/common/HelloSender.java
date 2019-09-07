package com.joker.common;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Joker Jing
 */
@Component
public class HelloSender {
 
	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
 
	public void send() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String context = "hello" + sdf.format(new Date());
		System.out.println("Sender : " + context);
		this.rabbitAmqpTemplate.convertAndSend("hello", context);
	}
 
}