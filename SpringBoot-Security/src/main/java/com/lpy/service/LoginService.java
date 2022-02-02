package com.lpy.service;

import com.lpy.entity.User;
import com.lpy.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public  List<User> getUserByUsername(String username) {
        return loginRepository.getUserByUsername(username);
    }

}
