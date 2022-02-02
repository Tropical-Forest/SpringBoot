package com.lpy.quartz;


import com.lpy.mail.SendJunkMailService;
import com.lpy.model.AyUser;
import com.lpy.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;

//@Component
//@Configuration
//@EnableScheduling
public class SendMailQuartz {
    //日志对象
    private  static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private AyUserService ayUserService;
    //每5秒执行一次
    @Scheduled(cron = "*/5 * *  * * *")
    public void reportCurrentByCron() throws MessagingException {
        List<AyUser> userList = ayUserService.findAll();
        if(userList == null || userList.size() <= 0) return;
        //发送邮件
        sendJunkMailService.sendJunkMail(userList);
        logger.info("定时器运行了！！！");
    }
}
