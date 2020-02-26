package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (DwVideoTypeMaxPlayCount)实体类
 *
 * @author makejava
 * @since 2020-02-21 19:40:16
 */
@Getter
@Setter
@Table(name="dw_video_type_max_play_count")
public class VideoTypeMaxPlayCount implements Serializable {
    private static final long serialVersionUID = 221912417965479268L;

    @Id
    private String videoAid;
    
    private String videoName;
    
    private String videoUrl;
    
    private String videoTypeId;
    
    private String videoTypeName;
    
    private String maxPlayCount;

}