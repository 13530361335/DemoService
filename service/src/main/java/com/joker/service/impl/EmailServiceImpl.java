package com.joker.service.impl;

import com.joker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @auther: Joker Jing
 * @Date: 2019/7/29
 * @Description:
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String getTextByTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }

    @Override
    public void sendEmail(SimpleMailMessage message) throws Exception{
        javaMailSender.send(message);
    }

    @Override
    public void sendEmail(String to, String title, String content) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        sendEmail(message);
    }
}
