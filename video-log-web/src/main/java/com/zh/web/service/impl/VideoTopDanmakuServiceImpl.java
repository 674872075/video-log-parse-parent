package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTopDanmakuMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTopDanmakuResult;
import com.zh.web.entity.VideoTopDanmaku;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopDanmakuService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/3 18:07
 * @Description
 */

@Service
public class VideoTopDanmakuServiceImpl implements VideoTopDanmakuService {

    @Resource
    private VideoTopDanmakuMapper videoTopDanmakuMapper;

    @Override
    public VideoTopDanmakuResult queryById(String videoAid) {
        VideoTopDanmaku videoTopDanmaku = videoTopDanmakuMapper.selectByPrimaryKey(videoAid);
        if(Objects.isNull(videoTopDanmaku)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTopDanmakuResult(VideoCode.VIDEO_QUERY_SUCCESS,videoTopDanmaku);

    }

    @Override
    public DataGridResult<VideoTopDanmaku> queryVideoTopDanmakus(VideoTopDanmaku videoTopDanmaku, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTopDanmaku.class,false);
        //example.excludeProperties("videoTypeName");
        example.setOrderByClause("danmaku asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTopDanmaku);
        List<VideoTopDanmaku> videoTopDanmakus =videoTopDanmakuMapper.selectByExample(example);
        PageInfo<VideoTopDanmaku> pages = PageInfo.of(videoTopDanmakus);
        return new DataGridResult<VideoTopDanmaku>(pages.getTotal(),pages.getList());

    }

    @Override
    public DataGridResult<VideoTopDanmaku> queryAll() {
        List<VideoTopDanmaku> videoTopDanmakus = videoTopDanmakuMapper.findAll();
        return new DataGridResult<VideoTopDanmaku>(Long.valueOf(videoTopDanmakus.size()),videoTopDanmakus);
    }

    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        List<VideoTypeCount> videoCategoryCounts = videoTopDanmakuMapper.getVideoCategoryCount();
        return new DataGridResult<VideoTypeCount>(Long.valueOf(videoCategoryCounts.size()),videoCategoryCounts);
    }
}
