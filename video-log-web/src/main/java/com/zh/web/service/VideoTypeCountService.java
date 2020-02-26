package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTypeCountResult;
import com.zh.web.entity.VideoTypeCount;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
public interface VideoTypeCountService {

    /**
     * 通过视频类型ID查询视频数据
     * @param videoTypeId 主键 视频类型Id
     * @return 返回统一结果集
     */
    VideoTypeCountResult selectOne(String videoTypeId);

    /**
     * 分页查询
     * @param videoTypeCount
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTypeCount> queryVideoTypeCounts(VideoTypeCount videoTypeCount, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTypeCount> queryAll();


}