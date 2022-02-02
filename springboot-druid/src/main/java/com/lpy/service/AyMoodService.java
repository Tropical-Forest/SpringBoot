package com.lpy.service;

import com.lpy.model.AyMood;

/**
 * 描述:微信说说服务层
 */
public interface AyMoodService {
    AyMood save(AyMood ayMood);
    String asynSave(AyMood ayMood);
}
