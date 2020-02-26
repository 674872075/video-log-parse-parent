package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (DwVideoTypeCount)实体类
 *
 * @author makejava
 * @since 2020-02-21 19:40:16
 */
@Getter
@Setter
@Table(name = "dw_video_type_count")
public class VideoTypeCount implements Serializable {
    private static final long serialVersionUID = -90082445885741627L;

    @Id
    private String videoTypeId;
    
    private String videoTypeName;
    
    private String videoCount;

}