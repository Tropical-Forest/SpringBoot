package com.example.ayuserservice.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.ayuserapi.api.AyUserDubboService;
import com.example.ayuserapi.domain.AyUser;


@Service(version = "2.6.12")
public class AyUserDubboServiceImpl implements AyUserDubboService {

    @Override
    public AyUser findByUserNameAndPassword(String name, String password) {

        //连接数据库，查询用户数据，此处省略
        AyUser ayUser = new AyUser();
        ayUser.setName("阿毅");
        ayUser.setPassword("123456");
        return ayUser;

    }
}
