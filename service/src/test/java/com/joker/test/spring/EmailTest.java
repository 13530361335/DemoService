package com.joker.test.spring;

import com.joker.service.EmailService;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joker Jing
 * @date 2019/7/9
 */
public class EmailTest extends BaseTest {

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String sender;

    //发送邮件的模板引擎
    @Autowired
    private FreeMarkerConfigurer configurer;

    @Test
    public void sendSimpleMail() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "您好，感谢您的注册，这是一封验证邮件，请点击下面的连接完成注册，感谢您的支持！");
        String text = emailService.getTextByTemplate("emailFreeMarker.ftl", model);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo("minjing@isoftstone.com"); //自己给自己发送邮件
        message.setSubject("哈哈");
        message.setText(text);
        System.out.println(text);
        emailService.sendEmail(message);
    }

    @Test
    public void sendEmail() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "您好，感谢您的注册，这是一封验证邮件，请点击下面的连接完成注册，感谢您的支持！");
        String text = emailService.getTextByTemplate("emailFreeMarker.ftl", model);
        System.out.println(text);
//        emailService.sendEmail("761878367@qq.com", "测试", "哈哈123456");
    }

    @Test
    public void get() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("info", "您好，感谢您的注册，这是一封验证邮件，请点击下面的连接完成注册，感谢您的支持！");
        Template template = configurer.getConfiguration().getTemplate("emailFreeMarker.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        System.out.println(emailContent);
        emailService.sendEmail("761878367@qq.com", "测试", emailContent);
    }

}
