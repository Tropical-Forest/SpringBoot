package com.lpy.serviceImpl;

import com.lpy.model.AyUserAttachmentRel;
import com.lpy.repository.AyUserAttachmentRelRepository;
import com.lpy.service.AyUserAttachmentRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AyUserAttachmentRelServiceImpl implements AyUserAttachmentRelService {

    @Resource
    private AyUserAttachmentRelRepository ayUserAttachmentRelRepository;

    @Override
    public AyUserAttachmentRel save(AyUserAttachmentRel ayUserAttachmentRel) {

        return ayUserAttachmentRelRepository.save(ayUserAttachmentRel);
        //return null;
    }
}
