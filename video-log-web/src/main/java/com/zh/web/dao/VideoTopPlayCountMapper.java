package com.zh.web.dao;

import com.zh.web.entity.VideoTopPlayCount;
import com.zh.web.entity.VideoTypeCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (DwVideoTopPlayCount)表数据库访问层
 *
 * @author zhouhao
 * @since 2020-02-21 19:40:13
 */
@Mapper
public interface VideoTopPlayCountMapper extends IBaseDao<VideoTopPlayCount> {

    List<VideoTypeCount> getVideoCategoryCount();

    List<VideoTopPlayCount> findAll();

}