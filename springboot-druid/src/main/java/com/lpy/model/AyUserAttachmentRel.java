package com.lpy.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class AyUserAttachmentRel {
    @Id
    private String id;
    private String userId;
    private String fileName;
}
