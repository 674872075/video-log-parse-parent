package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopRankScoreResult;
import com.zh.web.entity.VideoTopRankScore;
import com.zh.web.entity.VideoTypeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotBlank;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/03 13:50
 * @Description
 */
@Api(value = "video_top_rank_score页面管理接口", description = "video_top_rank_score页面管理接口，提供页面查询功能")
public interface VideoTopRankScoreControllerApi {

    @ApiOperation("根据video_aid查询video_top_rank_score信息")
    @ApiImplicitParam(name = "id", value = "视频aid"
            , required = true, paramType = "path", dataType = "String")
        // @ApiImplicitParams
    VideoTopRankScoreResult selectOne(@NotBlank String aid);

    @ApiOperation("分页查询video_top_rank_score信息")
    DataGridResult<VideoTopRankScore> queryVideoTopRankScores(VideoTopRankScore videoTopRankScore, int page, int rows);

    @ApiOperation("查询video_top_rank_score所有视频信息")
    DataGridResult<VideoTopRankScore> queryAll();

    @ApiOperation("按所有分类查询视频个数信息")
    DataGridResult<VideoTypeCount> getVideoCategoryCount();
}
