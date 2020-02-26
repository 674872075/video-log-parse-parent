package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTypeMaxPlayCountResult;
import com.zh.web.entity.VideoTypeMaxPlayCount;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
public interface VideoTypeMaxPlayCountService {

    /**
     * 通过视频ID查询视频数据
     * @param videoAid 主键 视频Id
     * @return 返回统一结果集
     */
    VideoTypeMaxPlayCountResult queryById(String videoAid);

    /**
     * 分页查询
     * @param videoTypeMaxPlayCount
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    DataGridResult<VideoTypeMaxPlayCount> queryVideoTypeMaxPlayCounts(VideoTypeMaxPlayCount videoTypeMaxPlayCount, int page, int rows);

    /**
     * 查询所有视频记录
     * @return
     */
    DataGridResult<VideoTypeMaxPlayCount> queryAll();
}