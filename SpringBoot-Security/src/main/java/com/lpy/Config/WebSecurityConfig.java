package com.lpy.Config;



import ch.qos.logback.core.encoder.Encoder;

import com.lpy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    LoginService loginService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            List<com.lpy.entity.User> users = loginService.getUserByUsername(username);
            if (users == null || users.size() == 0) {
                throw new UsernameNotFoundException("用户名未找到");
            }
            String password = users.get(0).getPassword();
            return User.withUsername(username).password(password).roles("").build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
