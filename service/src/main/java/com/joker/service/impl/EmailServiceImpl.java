package com.joker.service.impl;

import com.joker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * created by Joker on 2019/7/9
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String getTextByTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }

    @Override
    public void sendEmail(SimpleMailMessage message) {
        javaMailSender.send(message);
    }
}
