package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopCollectCountResult;
import com.zh.web.entity.VideoTopCollectCount;
import com.zh.web.entity.VideoTypeCount;

/**
 * @author zhouhao
 * @since 2020-03-03 11:20
 */
public interface VideoTopCollectCountService {
    
    /**
     * 通过视频ID查询视频数据
     * @param videoAid 主键 视频Id
     * @return 返回统一结果集
     */

    VideoTopCollectCountResult queryById(String videoAid);

    /**
     * 分页查询
     * @param videoTopCollectCount 条件
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTopCollectCount> queryVideoTopCollectCounts(VideoTopCollectCount videoTopCollectCount, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTopCollectCount> queryAll();

    /**
     * 获取各分类视频数,用于计算占比
     * @return
     */
    DataGridResult<VideoTypeCount> getVideoCategoryCount();

}