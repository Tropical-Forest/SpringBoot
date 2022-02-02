package com.lpy.serviceImpl;

import com.lpy.dao.AyUserDao;
import com.lpy.error.BusinessException;
import com.lpy.model.AyUser;
import com.lpy.repository.AyUserRepository;
import com.lpy.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;


@Service
public class AyUserServiceImpl implements AyUserService {

    @Autowired
    private AyUserRepository ayUserRepository;

    @Autowired
    private RedisTemplate redisTemplate;
    //需要添加的代码
    Logger logger = LogManager.getLogger(this.getClass());
    private  static final String ALL_USER = "ALL_USER_LIST";
    @Resource
    private  AyUserDao ayUserDao;

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        return ayUserDao.findByNamePassword(name,password);
    }

    @Override
    public AyUser findById(String id) {
        //step.1 查询Redis缓存中的所有数据
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if(ayUserList != null && ayUserList.size() > 0) {
            for (AyUser user: ayUserList) {
                if(user.getId().equals(id)) {
                    return user;
                }
            }
        }
        //step.2 查询数据库中的数据
        AyUser ayUser = ayUserRepository.findById(id).orElse(null);
        if(ayUser != null) {
            //step.3 将数据插入Redis缓存中
            redisTemplate.opsForList().leftPush(ALL_USER, ayUser);

        }
        return ayUser;
        //return ayUserRepository.findById(id).orElse(null);
    }



    @Override
    public List<AyUser> findAll() {
       // return ayUserRepository.findAll();
        try{
            logger.info("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUser> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            logger.info("完成任务，耗时：" +(end-start)+"毫秒");
            return ayUserList;
        }catch (Exception e) {
            logger.error("method [findAll] error", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    @Async
    public Future<List<AyUser>> findAsynAll() {
        try {
            logger.info("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUser> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            logger.info("完成任务，耗时："+(end-start)+"毫秒");
            return new AsyncResult<List<AyUser>>(ayUserList);
        }catch (Exception e) {
            logger.error("method [findAll] error",e);
            return new AsyncResult<List<AyUser>>(null);
        }
    }


    @Override
    public AyUser save(AyUser ayUser) {
        AyUser saveUser = ayUserRepository.save(ayUser);
        String error = null;
        error.split("/");
        return saveUser;
    }

    @Override
    public void delete(String id) {

        ayUserRepository.deleteById(id);
        logger.info("userId:"+id + "用户被删除");
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

    @Override
    @Retryable(value = BusinessException.class,maxAttempts = 5,backoff = @Backoff(delay = 5000,multiplier = 2))
    public AyUser findByNameAndPasswordRetry(String name, String password) {
        logger.info("[findByNameAndPasswordRetry] 方法失败重试了！ ");
        throw new BusinessException();
        //return null;
    }


}
