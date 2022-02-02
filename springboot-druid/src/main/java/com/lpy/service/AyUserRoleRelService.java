package com.lpy.service;

import com.lpy.model.AyUserRoleRel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AyUserRoleRelService {
    List<AyUserRoleRel> findByUserId(String userId);
}
