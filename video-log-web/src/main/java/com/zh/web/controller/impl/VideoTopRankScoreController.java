package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTopRankScoreControllerApi;
import com.zh.web.dto.VideoTopRankScoreResult;
import com.zh.web.entity.VideoTopRankScore;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopRankScoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/3 13:52
 * @Description
 */
@Validated
@RestController
@RequestMapping("dwVideoTopRankScore")
public class VideoTopRankScoreController implements VideoTopRankScoreControllerApi {

    @Resource
    private VideoTopRankScoreService videoTopRankScoreServiceImpl;

    @GetMapping("select_one/{id}")
    @Override
    public VideoTopRankScoreResult selectOne(@PathVariable("id") String aid) {
        return videoTopRankScoreServiceImpl.queryById(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTopRankScore> queryVideoTopRankScores(VideoTopRankScore videoTopRankScore, @PathVariable("page") int page,  @PathVariable("rows") int rows) {
        return videoTopRankScoreServiceImpl.queryVideoTopRankScores(videoTopRankScore,page,rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTopRankScore> queryAll() {
        return videoTopRankScoreServiceImpl.queryAll();
    }

    @GetMapping("getVideoCategoryCount")
    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        return videoTopRankScoreServiceImpl.getVideoCategoryCount();
    }
}
