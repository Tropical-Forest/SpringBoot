package com.lpy.serviceImpl;

import com.lpy.model.AyUserRoleRel;
import com.lpy.repository.AyUserRoleRelRepository;
import com.lpy.service.AyRoleService;
import com.lpy.service.AyUserRoleRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AyUserRoleRelServiceImpl implements AyUserRoleRelService {
    @Resource
    private AyUserRoleRelRepository ayUserRoleRelRepository;
    @Override
    public List<AyUserRoleRel> findByUserId(String userId) {
        return ayUserRoleRelRepository.findByUserId(userId);
    }
}
