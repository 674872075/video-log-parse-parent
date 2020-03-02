package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhouhao
 * @since 2020-03-01 15:19:16
 */
@Getter
@Setter
@Table(name = "dw_day_video_type_count")
public class DayVideoTypeCount implements Serializable {
    private static final long serialVersionUID = -500842425255741627L;

    @Id
    private String publishDate;

    @Id
    private String videoTypeId;

    private String videoTypeName;
    
    private String videoCount;

}