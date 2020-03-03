package com.zh.web.dao;

import com.zh.web.entity.VideoTopDanmaku;
import com.zh.web.entity.VideoTypeCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhouhao
 * @since 2020-03-03 18:02
 */
@Mapper
public interface VideoTopDanmakuMapper extends IBaseDao<VideoTopDanmaku> {

    List<VideoTypeCount> getVideoCategoryCount();

    List<VideoTopDanmaku> findAll();

}