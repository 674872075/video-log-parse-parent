package com.zh.web.dao;

import com.zh.web.entity.VideoTopCollectCount;
import com.zh.web.entity.VideoTypeCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhouhao
 * @since 2020-03-03 11:33
 */
@Mapper
public interface VideoTopCollectCountMapper extends IBaseDao<VideoTopCollectCount> {

    List<VideoTypeCount> getVideoCategoryCount();

    List<VideoTopCollectCount> findAll();

}