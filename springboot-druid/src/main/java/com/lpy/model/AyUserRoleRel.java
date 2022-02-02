package com.lpy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ay_user_role_rel")
public class AyUserRoleRel {
    @Id
    private String userId;
    private String roleId;

}
