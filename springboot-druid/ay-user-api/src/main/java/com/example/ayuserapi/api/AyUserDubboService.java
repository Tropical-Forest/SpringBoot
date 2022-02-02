package com.example.ayuserapi.api;

import com.example.ayuserapi.domain.AyUser;

/**
 * 用户接口
 */
public interface AyUserDubboService {
    AyUser findByUserNameAndPassword(String name, String password);
}
