package com.example.study02;

import com.example.study02.domain.AyUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class Study02ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Test
    public  void mySqlTest(){
        String sql = "select id,name,password from ay_user";
       List<AyUser> userList = jdbcTemplate.query(sql, new RowMapper<AyUser>(){

            @Override
            public AyUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                AyUser user = new AyUser();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
        System.out.println("查询成功:");
        for (AyUser user:userList) {
            System.out.println("[id]:"+user.getId()+";[name]:"+user.getName()+";[password]:"+user.getPassword());
        }
    }

}
