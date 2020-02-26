package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTypeMaxPlayCountMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTypeMaxPlayCountResult;
import com.zh.web.entity.VideoTypeMaxPlayCount;
import com.zh.web.service.VideoTypeMaxPlayCountService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
@Service("dwVideoTypeMaxPlayCountService")
public class VideoTypeMaxPlayCountServiceImpl implements VideoTypeMaxPlayCountService {

    @Resource
    private VideoTypeMaxPlayCountMapper videoTypeMaxPlayCountMapper;

    @Override
    public VideoTypeMaxPlayCountResult queryById(String videoAid) {
        VideoTypeMaxPlayCount videoTypeMaxPlayCount = videoTypeMaxPlayCountMapper.selectByPrimaryKey(videoAid);
        if(Objects.isNull(videoTypeMaxPlayCount)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTypeMaxPlayCountResult(VideoCode.VIDEO_QUERY_SUCCESS,videoTypeMaxPlayCount);
    }

    @Override
    public DataGridResult<VideoTypeMaxPlayCount> queryVideoTypeMaxPlayCounts(VideoTypeMaxPlayCount videoTypeMaxPlayCount, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTypeMaxPlayCount.class);
        example.setOrderByClause("max_play_count desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTypeMaxPlayCount);
        List<VideoTypeMaxPlayCount> videoTypeMaxPlayCounts = videoTypeMaxPlayCountMapper.selectByExample(example);
        PageInfo<VideoTypeMaxPlayCount> pages = PageInfo.of(videoTypeMaxPlayCounts);
        return new DataGridResult<VideoTypeMaxPlayCount>(pages.getTotal(),pages.getList());
    }

    @Override
    public DataGridResult<VideoTypeMaxPlayCount> queryAll() {
        Example example=new Example(VideoTypeMaxPlayCount.class);
        example.setOrderByClause("max_play_count asc");
        List<VideoTypeMaxPlayCount> videoTypeMaxPlayCounts = videoTypeMaxPlayCountMapper.selectByExample(example);
        return new DataGridResult<VideoTypeMaxPlayCount>(Long.valueOf(videoTypeMaxPlayCounts.size()),videoTypeMaxPlayCounts);
    }

}