package com.lpy.serverImpl;


import com.lpy.model.AyUser;
import com.lpy.repository.AyUserRepository;
import com.lpy.server.AyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AyUserServiceImpl implements AyUserService {

    @Autowired
    private AyUserRepository ayUserRepository;

    @Override
    public AyUser findById(String id) {
        return ayUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<AyUser> findAll() {
        return ayUserRepository.findAll();
    }

    @Override
    public AyUser save(AyUser ayUser) {
        return ayUserRepository.save(ayUser);
    }

    @Override
    public void delete(String id) {
        ayUserRepository.deleteById(id);
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String name) {
        return ayUserRepository.findByName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }


}
