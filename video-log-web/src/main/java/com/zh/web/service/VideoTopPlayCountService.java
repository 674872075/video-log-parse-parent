package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopPlayCountResult;
import com.zh.web.entity.VideoTopPlayCount;
import com.zh.web.entity.VideoTypeCount;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:14
 */
public interface VideoTopPlayCountService {
    
    /**
     * 通过视频ID查询视频数据
     * @param videoAid 主键 视频Id
     * @return 返回统一结果集
     */
    VideoTopPlayCountResult queryById(String videoAid);

    /**
     * 分页查询
     * @param videoTopPlayCount 条件
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTopPlayCount> queryVideoTopPlayCounts(VideoTopPlayCount videoTopPlayCount, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTopPlayCount> queryAll();

    /**
     * 获取各分类视频数,用于计算占比
     * @return
     */
    DataGridResult<VideoTypeCount> getVideoCategoryCount();

}