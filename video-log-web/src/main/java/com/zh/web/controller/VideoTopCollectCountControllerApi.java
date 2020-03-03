package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTopCollectCountResult;
import com.zh.web.entity.VideoTopCollectCount;
import com.zh.web.entity.VideoTypeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotBlank;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/03 11:13
 * @Description
 */
@Api(value = "video_top_collect_count页面管理接口", description = "video_top_collect_count页面管理接口，提供页面查询功能")
public interface VideoTopCollectCountControllerApi {

    @ApiOperation("根据video_aid查询video_top_collect_count信息")
    @ApiImplicitParam(name = "id", value = "视频aid"
            , required = true, paramType = "path", dataType = "String")
        // @ApiImplicitParams
    VideoTopCollectCountResult selectOne(@NotBlank String aid);

    @ApiOperation("分页查询video_top_collect_count信息")
    DataGridResult<VideoTopCollectCount> queryVideoTopCollectCounts(VideoTopCollectCount VideoTopCollectCount, int page, int rows);

    @ApiOperation("查询video_top_collect_count所有视频信息")
    DataGridResult<VideoTopCollectCount> queryAll();

    @ApiOperation("按所有分类查询视频个数信息")
    DataGridResult<VideoTypeCount> getVideoCategoryCount();
}
