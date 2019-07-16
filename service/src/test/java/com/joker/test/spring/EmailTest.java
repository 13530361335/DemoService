package com.joker.test.spring;

import com.joker.service.EmailService;
import com.joker.test.SpringBootBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;

/**
 * created by Joker on 2019/7/9
 */
public class EmailTest extends SpringBootBaseTest {

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String sender;


    @Test
    public void sendSimpleMail() throws Exception {
        Context context = new Context();
        context.setVariable("message", "测试123456");
        String text = emailService.getTextByTemplate("mailTemplate", context);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo("minjing@isoftstone.com"); //自己给自己发送邮件
        message.setSubject("哈哈");
        message.setText(text);
        System.out.println(text);
        emailService.sendEmail(message);
    }


}
