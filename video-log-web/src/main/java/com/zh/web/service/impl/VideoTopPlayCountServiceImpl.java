package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTopPlayCountMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTopPlayCountResult;
import com.zh.web.entity.VideoTopPlayCount;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopPlayCountService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * (DwVideoTopPlayCount)表服务实现类
 *
 * @author makejava
 * @since 2020-02-21 19:40:14
 */
@Service("dwVideoTopPlayCountService")
public class VideoTopPlayCountServiceImpl implements VideoTopPlayCountService {
    @Resource
    private VideoTopPlayCountMapper videoTopPlayCountMapper;


    @Override
    public VideoTopPlayCountResult queryById(String videoAid) {
        VideoTopPlayCount VideoTopPlayCount = videoTopPlayCountMapper.selectByPrimaryKey(videoAid);
        if(Objects.isNull(VideoTopPlayCount)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTopPlayCountResult(VideoCode.VIDEO_QUERY_SUCCESS,VideoTopPlayCount);
    }

    @Override
    public DataGridResult<VideoTopPlayCount> queryVideoTopPlayCounts(VideoTopPlayCount videoTopPlayCount, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTopPlayCount.class,false);
        //example.excludeProperties("videoTypeName");
        example.setOrderByClause("play_count asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTopPlayCount);
        List<VideoTopPlayCount> VideoTopPlayCounts = videoTopPlayCountMapper.selectByExample(example);
        PageInfo<VideoTopPlayCount> pages = PageInfo.of(VideoTopPlayCounts);
        return new DataGridResult<VideoTopPlayCount>(pages.getTotal(),pages.getList());
    }

    @Override
    public DataGridResult<VideoTopPlayCount> queryAll() {
        List<VideoTopPlayCount> videoTopPlayCounts = videoTopPlayCountMapper.findAll();
        return new DataGridResult<VideoTopPlayCount>(Long.valueOf(videoTopPlayCounts.size()),videoTopPlayCounts);
    }

    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        List<VideoTypeCount> videoCategoryCounts = videoTopPlayCountMapper.getVideoCategoryCount();
        return new DataGridResult<VideoTypeCount>(Long.valueOf(videoCategoryCounts.size()),videoCategoryCounts);
    }
}