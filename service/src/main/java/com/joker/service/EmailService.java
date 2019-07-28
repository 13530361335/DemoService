package com.joker.service;

import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;

/**
 * @auther: Joker Jing
 * @Date: 2019/7/29
 * @Description:
 */
public interface EmailService {

    /**
     * 生成模板
     * @param template
     * @param context
     * @return
     */
    String getTextByTemplate(String template, Context context);

    /**
     * 发送邮件
     * @param message
     * @throws Exception
     */
    void sendEmail(SimpleMailMessage message) throws Exception;

    /**
     * 发送邮件
     * @param to
     * @param title
     * @param content
     * @throws Exception
     */
    void sendEmail(String to,String title,String content) throws Exception;

}
