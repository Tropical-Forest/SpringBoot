package com.example.ayuserapi.domain;


import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class AyUser {
    private String id;
    private String name;
    private String password;
    private String mail;
}
