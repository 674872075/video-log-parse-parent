package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.entity.DayVideoTypeCount;

import java.util.List;

/**
 * @author zhouhao
 * @since 2020-03-01 15:25:16
 */
public interface DayVideoTypeCountService {

    /**
     * 获取所有日期
     * @return
     */
    List<DayVideoTypeCount> getALLPublishDate();

    /**
     * 根据发布日期查询所有分类视频个数
     * @param publishDate
     * @return
     */
    DataGridResult<DayVideoTypeCount> queryAllByPublishDate(String publishDate);
}