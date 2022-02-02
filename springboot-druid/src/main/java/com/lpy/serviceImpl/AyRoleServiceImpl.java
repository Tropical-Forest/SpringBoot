package com.lpy.serviceImpl;

import com.lpy.model.AyRole;
import com.lpy.repository.AyRoleRepository;
import com.lpy.service.AyRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AyRoleServiceImpl implements AyRoleService {

    @Resource
    private AyRoleRepository ayRoleRepository;
    @Override
    public AyRole find(String id) {
        return ayRoleRepository.findById(id).get();
    }
}
