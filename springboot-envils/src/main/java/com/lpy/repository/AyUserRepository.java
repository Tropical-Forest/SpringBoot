package com.lpy.repository;

import com.lpy.model.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AyUserRepository extends JpaRepository<AyUser,String> {
    List<AyUser> findByName(String name);
    List<AyUser> findByNameLike(String name);
    List<AyUser> findByIdIn(Collection<String> ids);
}
