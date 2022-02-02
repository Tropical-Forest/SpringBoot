package com.lpy.listener;


import com.lpy.model.AyUser;
import com.lpy.service.AyUserService;
import com.lpy.util.SpringUtil;
import io.netty.util.internal.StringUtil;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;


//@Component
public class AyUserListener implements ServletContextListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AyUserService ayUserService;
    private  static final String ALL_USER = "ALL_USER_LIST";
    //需要添加的代码
    Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //查询数据库所有的用户
        List<AyUser> ayUserList = ayUserService.findAll();
        // 清除缓存中的用户数据
        redisTemplate.delete(ALL_USER);
        // 将数据存放到Redis缓存中
        redisTemplate.opsForList().leftPushAll(ALL_USER, ayUserList);
        // 在真实项目中需要注释掉，查询所有的用户数据
        List<AyUser> queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        //System.out.println("缓存中目前的用户数有: " + queryUserList.size() + " 人 ");

        //System.out.println("ServletContext 上下文初始化 ");
        logger.info("ServletContext 上下文初始化 ");
        logger.info("缓存中目前的用户数有: " + queryUserList.size() + " 人 ");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //System.out.println("ServletContext 上下文销毁");
        logger.info("ServletContext 上下文销毁");
    }
}
