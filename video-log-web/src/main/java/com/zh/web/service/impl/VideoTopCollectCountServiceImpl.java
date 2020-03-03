package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTopCollectCountMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTopCollectCountResult;
import com.zh.web.entity.VideoTopCollectCount;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopCollectCountService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouhao
 * @since 2020-03-03 11:31
 */
@Service
public class VideoTopCollectCountServiceImpl implements VideoTopCollectCountService {

    @Resource
    private VideoTopCollectCountMapper videoTopCollectCountMapper;


    @Override
    public VideoTopCollectCountResult queryById(String videoAid) {
        VideoTopCollectCount videoTopCollectCount = videoTopCollectCountMapper.selectByPrimaryKey(videoAid);
        if(Objects.isNull(videoTopCollectCount)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTopCollectCountResult(VideoCode.VIDEO_QUERY_SUCCESS,videoTopCollectCount);
    }

    @Override
    public DataGridResult<VideoTopCollectCount> queryVideoTopCollectCounts(VideoTopCollectCount videoTopCollectCount, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTopCollectCount.class,false);
        //example.excludeProperties("videoTypeName");
        example.setOrderByClause("video_collect_count asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTopCollectCount);
        List<VideoTopCollectCount> videoTopCollectCounts = videoTopCollectCountMapper.selectByExample(example);
        PageInfo<VideoTopCollectCount> pages = PageInfo.of(videoTopCollectCounts);
        return new DataGridResult<VideoTopCollectCount>(pages.getTotal(),pages.getList());
    }

    @Override
    public DataGridResult<VideoTopCollectCount> queryAll() {
        List<VideoTopCollectCount> videoTopCollectCounts = videoTopCollectCountMapper.findAll();
        return new DataGridResult<VideoTopCollectCount>(Long.valueOf(videoTopCollectCounts.size()),videoTopCollectCounts);
    }

    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        List<VideoTypeCount> videoCategoryCounts = videoTopCollectCountMapper.getVideoCategoryCount();
        return new DataGridResult<VideoTypeCount>(Long.valueOf(videoCategoryCounts.size()),videoCategoryCounts);
    }
}