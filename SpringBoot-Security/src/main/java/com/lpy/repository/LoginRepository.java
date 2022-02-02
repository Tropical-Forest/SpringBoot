package com.lpy.repository;

import com.lpy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUserByUsername(String username) {
        String sql = "select id, username, password from user where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

}
