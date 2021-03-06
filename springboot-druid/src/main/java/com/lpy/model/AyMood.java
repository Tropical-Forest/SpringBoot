package com.lpy.model;




import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ay_mood")
public class AyMood implements Serializable {
    @Id
    private String id;
    //说说内容
    private  String content;
    //用户id
    private String userId;
    //点赞个数
    private  Integer praiseNum;
    //发表时间
    private Date publishTime;

    public AyMood(){

    }

}
