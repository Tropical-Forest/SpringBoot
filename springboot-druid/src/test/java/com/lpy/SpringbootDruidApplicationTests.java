package com.lpy;

import com.lpy.model.AyMood;
import com.lpy.model.AyUser;
import com.lpy.model.AyUserAttachmentRel;
import com.lpy.producer.AyMoodProducer;
import com.lpy.service.AyMoodService;
import com.lpy.service.AyUserAttachmentRelService;
import com.lpy.service.AyUserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootTest
class SpringbootDruidApplicationTests {

    @Resource
    private AyUserService ayUserService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRepository() {
        //查询所有数据
        List<AyUser> userList1 = ayUserService.findAll();
        System.out.println("findAll() :" + userList1.size());
        //通过name查询数据
        List<AyUser> userList2 = ayUserService.findByName("阿毅");
        System.out.println("findByName() :"+userList2.size());
        Assert.isTrue(userList2.get(0).getName().equals("阿毅"),"data error!");
        //通过name模糊查询数据
        List<AyUser> userList3 = ayUserService.findByNameLike("%毅%");
        System.out.println("findByNameLike() :" + userList3.size());
        Assert.isTrue(userList3.get(0).getName().equals("阿毅"),"data error!");
        //通过id列表查询数据
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        List<AyUser> userList4 = ayUserService.findByIdIn(ids);
        System.out.println("findByIdIn() :" + userList4.size());
        //分页查询数据
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<AyUser> userList5  = ayUserService.findAll(pageRequest);
        System.out.println("page findAll():"+userList5.getTotalPages()+"/"+userList5.getSize());
        //新增数据
        AyUser ayUser = new AyUser();
        ayUser.setId("3");
        ayUser.setName("test");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);
        //删除数据
        ayUserService.delete("3");

    }

    @Test
    public void testTransaction() {
        AyUser ayUser = new AyUser();
        ayUser.setId("3");
        ayUser.setName("阿花");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);
    }

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        //增 key：name,value:ay
        redisTemplate.opsForValue().set("name","ay");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
        //删除
        redisTemplate.delete("name");
        //更新
        redisTemplate.opsForValue().set("name","al");
        //查询
        name = stringRedisTemplate.opsForValue().get(name);
        System.out.println(name);

    }

    @Test
    public void testFindById() {
        Long redisUserSize = 0L;
        // 查询id=1的数据, 该数据存在于Redis缓存中
        AyUser ayUser = ayUserService.findById("1");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为： " + redisUserSize);
        System.out.println("---->>>id: " + ayUser.getId() + "name:" + ayUser.getName());

        // 查询id = 2 的数据,该数据存在于Redis缓存中
        AyUser ayUser1 = ayUserService.findById("2");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为： " + redisUserSize);
        System.out.println("---->>>id: " + ayUser1.getId() + "name:" + ayUser1.getName());

        //查询id=4的数据，不存在于Redis缓存中，存在于数据库中
        //所以会把在数据库中查询的数据更新到缓存中
        AyUser ayUser3 = ayUserService.findById("4");
        System.out.println("---->>>id: " + ayUser3.getId() + "name:" + ayUser3.getName());
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为： " + redisUserSize);

    }

    Logger logger = LogManager.getLogger(this.getClass());
    @Test
    public void testLog4j() {
        ayUserService.delete("4");
        logger.info("delete success!!!");
    }

    @Test
    public void testMybatis() {
        AyUser ayUser = ayUserService.findByNameAndPassword("阿毅","123456");
        logger.info(ayUser.getId() + ayUser.getName());
    }

    @Resource
    private  AyMoodService ayMoodService;
    @Test
    public void testAyMood() {
        AyMood ayMood = new AyMood();
        ayMood.setId("1");
        ayMood.setUserId("1");
        ayMood.setPraiseNum(0);
        //说说内容
        ayMood.setContent("这是我的第一条微信说说");
        ayMood.setPublishTime(new Date());
        AyMood mood = ayMoodService.save(ayMood);
    }

    @Resource
    private AyMoodProducer ayMoodProducer;
    @Test
    public void testActiveMQ() {
        Destination destination = new ActiveMQQueue(("ay.queue"));
        ayMoodProducer.sendMessage(destination,"hello,mq!!!!");
        System.out.println("异步测试");
    }

    @Test
    public void testActiveMQAsynSave() {
        AyMood ayMood = new AyMood();
        ayMood.setId("2");
        ayMood.setUserId("2");
        ayMood.setPraiseNum(0);
        ayMood.setContent("这是我的第一条微信说说！！！");
        ayMood.setPublishTime(new Date());
        String msg = ayMoodService.asynSave(ayMood);
        System.out.println("异步发表说说：" + msg);
    }

    @Test
    public void testAsync() {
        long startTime = System.currentTimeMillis();
        logger.info("第一次查询所有用户！");
        List<AyUser> ayUserList1 = ayUserService.findAll();
        logger.info("第二次查询所有用户！");
        List<AyUser> ayUserList2 = ayUserService.findAll();
        logger.info("第三次查询所有用户！");
        List<AyUser> ayUserList3 = ayUserService.findAll();
        long endTime = System.currentTimeMillis();
        logger.info("总共消耗:"+(endTime-startTime)+"毫秒");
    }

    @Test
    public void testAsync2() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        logger.info("第一次查询所有用户！");
        Future<List<AyUser>> ayUserList1 = ayUserService.findAsynAll();
        logger.info("第二次查询所有用户！");
        Future<List<AyUser>> ayUserList2 = ayUserService.findAsynAll();
        logger.info("第三次查询所有用户！");
        Future<List<AyUser>> ayUserList3 = ayUserService.findAsynAll();
        while (true){
            if(ayUserList1.isDone() && ayUserList2.isDone()&& ayUserList3.isDone()) {
                break;
            }else{
                Thread.sleep(10);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("总共消耗:"+(endTime-startTime)+"毫秒");
    }

    @Resource
    private AyUserAttachmentRelService ayUserAttachmentRelService;

    @Test
    public void testMongoDB() {
        AyUserAttachmentRel ayUserAttachmentRel = new AyUserAttachmentRel();
        ayUserAttachmentRel.setId("1");
        ayUserAttachmentRel.setUserId("1");
        ayUserAttachmentRel.setFileName("个人简历.doc");
        ayUserAttachmentRelService.save(ayUserAttachmentRel);
        System.out.println("保存成功");
    }

}
