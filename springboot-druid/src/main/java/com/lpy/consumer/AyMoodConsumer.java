package com.lpy.consumer;

import com.lpy.model.AyMood;
import com.lpy.service.AyMoodService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AyMoodConsumer {
    @JmsListener(destination = "ay.queue")
    public void receiveQueue(String text) {
        System.out.println("用户发表说说【"+text+"】成功");
    }

    @Resource
    private AyMoodService ayMoodService;
    @JmsListener(destination = "ay.queue.asyn.save")
    public void receiveQueue(AyMood ayMood) {
        System.out.println("异步保存，异步保存");
        ayMoodService.save(ayMood);

    }
}
