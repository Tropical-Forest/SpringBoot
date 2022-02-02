package com.lpy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "ay_role")
public class AyRole {
    @Id
    private String id;
    private String name;
}
