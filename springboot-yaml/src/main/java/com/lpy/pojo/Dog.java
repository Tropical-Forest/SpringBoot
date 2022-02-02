package com.lpy.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Dog {
    @Value("旺财")
    private String name;
    @Value("3")
    private Integer age;
}
