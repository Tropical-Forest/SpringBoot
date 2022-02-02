package com.lpy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class AyUser implements Serializable {

    @Id
    private String id;
    private String name;
    private String password;

}
