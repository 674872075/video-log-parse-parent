package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopDanmakuResult;
import com.zh.web.entity.VideoTopDanmaku;
import com.zh.web.entity.VideoTypeCount;

/**
 * @author zhouhao
 * @since 2020-03-03 18:04
 */
public interface VideoTopDanmakuService {
    
    /**
     * 通过视频ID查询视频数据
     * @param videoAid 主键 视频Id
     * @return 返回统一结果集
     */

    VideoTopDanmakuResult queryById(String videoAid);

    /**
     * 分页查询
     * @param videoTopDanmaku 条件
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTopDanmaku> queryVideoTopDanmakus(VideoTopDanmaku videoTopDanmaku, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTopDanmaku> queryAll();

    /**
     * 获取各分类视频数,用于计算占比
     * @return
     */
    DataGridResult<VideoTypeCount> getVideoCategoryCount();

}