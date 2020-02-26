package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTypeCountMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTypeCountResult;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTypeCountService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * (DwVideoTypeCount)表服务实现类
 *
 * @author makejava
 * @since 2020-02-21 19:40:16
 */
@Service("dwVideoTypeCountService")
public class VideoTypeCountServiceImpl implements VideoTypeCountService {
    @Resource
    private VideoTypeCountMapper videoTypeCountMapper;

    @Override
    public VideoTypeCountResult selectOne(String videoTypeId) {
        VideoTypeCount VideoTypeCount = videoTypeCountMapper.selectByPrimaryKey(videoTypeId);
        if(Objects.isNull(VideoTypeCount)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTypeCountResult(VideoCode.VIDEO_QUERY_SUCCESS,VideoTypeCount);
    }

    @Override
    public DataGridResult<VideoTypeCount> queryVideoTypeCounts(VideoTypeCount videoTypeCount, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTypeCount.class);
        example.setOrderByClause("video_count desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTypeCount);
        List<VideoTypeCount> VideoTypeCounts = videoTypeCountMapper.selectByExample(example);
        PageInfo<VideoTypeCount> pages = PageInfo.of(VideoTypeCounts);
        return new DataGridResult<VideoTypeCount>(pages.getTotal(),pages.getList());
    }

    @Override
    public DataGridResult<VideoTypeCount> queryAll() {
        List<VideoTypeCount> VideoTypeCounts = videoTypeCountMapper.selectAll();
        return new DataGridResult<VideoTypeCount>(Long.valueOf(VideoTypeCounts.size()),VideoTypeCounts);
    }
}