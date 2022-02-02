package com.lpy.service;

import com.lpy.model.AyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

@Service
public interface AyUserService {
    AyUser findByNameAndPassword(String name, String password);
    AyUser findById(String id);

    List<AyUser> findAll();
    //异步查询
    Future<List<AyUser>> findAsynAll();
    AyUser save(AyUser ayUser);
    void delete(String id);

    //分页
   Page<AyUser> findAll(Pageable pageable);
   List<AyUser> findByName(String name);
   List<AyUser> findByNameLike(String name);
   List<AyUser> findByIdIn(Collection<String> ids);

   AyUser findByNameAndPasswordRetry(String name, String password);
}
