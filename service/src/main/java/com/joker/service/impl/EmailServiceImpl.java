package com.joker.service.impl;

import com.joker.service.EmailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description:
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String getTextByTemplate(String templateName, Object data) throws IOException, TemplateException {
        Template template = configurer.getConfiguration().getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
    }

    @Override
    public void sendEmail(SimpleMailMessage message) throws Exception {
        javaMailSender.send(message);
    }

    @Async
    @Override
    public void sendEmail(String to, String title, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        sendEmail(message);
    }
}
