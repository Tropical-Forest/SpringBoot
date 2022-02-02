package com.lpy.mail;

import com.lpy.model.AyUser;
import com.lpy.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

//@Service
public class SendJunkMailServiceImpl implements SendJunkMailService{

    @Autowired
    JavaMailSender mailSender;
    @Resource
    private AyUserService ayUserService;
    @Value("${spring.mail.username}")
    private String from;
    public static final Logger logger = LogManager.getLogger(SendJunkMailServiceImpl.class);

    @Override
    public boolean sendJunkMail(List<AyUser> ayUserList) {
        try{
            if(ayUserList == null || ayUserList.size()<=0) return Boolean.FALSE;
        for (AyUser ayUser:ayUserList) {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            //邮件发送方
            message.setFrom(from);
            message.setSubject("哈哈，这是一个测试");
            //邮件接收方
            message.setTo("13432857303@163.com");
            message.setText(ayUser.getName()+",这是Mail测试");
            this.mailSender.send(mimeMessage);
        }

        }catch (Exception ex) {
            logger.error("sendJunkMail error and ayUser=%s",ayUserList,ex);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
