package com.zh.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.common.dto.DataGridResult;
import com.zh.common.exception.ExceptionCast;
import com.zh.web.dao.VideoTopRankScoreMapper;
import com.zh.web.dto.VideoCode;
import com.zh.web.dto.VideoTopRankScoreResult;
import com.zh.web.entity.VideoTopRankScore;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopRankScoreService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/3 13:43
 * @Description
 */
@Service
public class VideoTopRankScoreServiceImpl implements VideoTopRankScoreService {

    @Resource
    private VideoTopRankScoreMapper videoTopRankScoreMapper;

    @Override
    public VideoTopRankScoreResult queryById(String videoAid) {
        VideoTopRankScore videoTopRankScore = videoTopRankScoreMapper.selectByPrimaryKey(videoAid);
        if(Objects.isNull(videoTopRankScore)){
            ExceptionCast.cast(VideoCode.VIDEO_QUERY_NOT_EXIST);
        }
        return new VideoTopRankScoreResult(VideoCode.VIDEO_QUERY_SUCCESS,videoTopRankScore);

    }

    @Override
    public DataGridResult<VideoTopRankScore> queryVideoTopRankScores(VideoTopRankScore videoTopRankScore, int page, int rows) {
        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);
        Example example=new Example(VideoTopRankScore.class,false);
        //example.excludeProperties("videoTypeName");
        example.setOrderByClause("rank_score asc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(videoTopRankScore);
        List<VideoTopRankScore> videoTopRankScores = videoTopRankScoreMapper.selectByExample(example);
        PageInfo<VideoTopRankScore> pages = PageInfo.of(videoTopRankScores);
        return new DataGridResult<VideoTopRankScore>(pages.getTotal(),pages.getList());

    }

    @Override
    public DataGridResult<VideoTopRankScore> queryAll() {
        List<VideoTopRankScore> videoTopCollectCounts = videoTopRankScoreMapper.findAll();
        return new DataGridResult<VideoTopRankScore>(Long.valueOf(videoTopCollectCounts.size()),videoTopCollectCounts);

    }

    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        List<VideoTypeCount> videoCategoryCounts = videoTopRankScoreMapper.getVideoCategoryCount();
        return new DataGridResult<VideoTypeCount>(Long.valueOf(videoCategoryCounts.size()),videoCategoryCounts);
    }
}
