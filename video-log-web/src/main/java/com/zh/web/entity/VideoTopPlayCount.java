package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (DwVideoTopPlayCount)实体类
 *
 * @author makejava
 * @since 2020-02-21 19:40:12
 */
@Getter
@Setter
@Table(name = "dw_video_top_play_count")
public class VideoTopPlayCount implements Serializable {
    private static final long serialVersionUID = 265657338986130831L;

    @Id
    private String videoAid;
    
    private String videoName;
    
    private String videoUrl;
    
    private String videoTypeId;

    private String videoTypeName;

    private String videoAuthorMid;
    
    private String videoAuthor;
    
    private String playCount;
}