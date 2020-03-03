package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author zhouhao
 * @since 2020-03-03 18:01
 */
@Getter
@Setter
@Table(name = "dw_video_top_danmaku")
public class VideoTopDanmaku implements Serializable {
    private static final long serialVersionUID = 265642435216130831L;

    @Id
    private String videoAid;
    
    private String videoName;
    
    private String videoUrl;
    
    private String videoTypeId;

    //不与数据库字段对应,数据库表不存在此字段
    @Transient
    private String videoTypeName;

    private String videoAuthorMid;
    
    private String videoAuthor;
    
    private String danmaku;
}