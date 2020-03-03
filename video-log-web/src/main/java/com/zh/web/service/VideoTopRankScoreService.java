package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopRankScoreResult;
import com.zh.web.entity.VideoTopRankScore;
import com.zh.web.entity.VideoTypeCount;

/**
 * @author zhouhao
 * @since 2020-03-03 13:42
 */
public interface VideoTopRankScoreService {
    
    /**
     * 通过视频ID查询视频数据
     * @param videoAid 主键 视频Id
     * @return 返回统一结果集
     */

    VideoTopRankScoreResult queryById(String videoAid);

    /**
     * 分页查询
     * @param videoTopRankScore 条件
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTopRankScore> queryVideoTopRankScores(VideoTopRankScore videoTopRankScore, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTopRankScore> queryAll();

    /**
     * 获取各分类视频数,用于计算占比
     * @return
     */
    DataGridResult<VideoTypeCount> getVideoCategoryCount();

}