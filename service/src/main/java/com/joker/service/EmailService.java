package com.joker.service;

import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;

/**
 * created by Joker on 2019/7/9
 */
public interface EmailService {

    String getTextByTemplate(String template, Context context);

    void sendEmail(SimpleMailMessage message) throws Exception;

    void sendEmail(String to,String title,String content) throws Exception;

}
