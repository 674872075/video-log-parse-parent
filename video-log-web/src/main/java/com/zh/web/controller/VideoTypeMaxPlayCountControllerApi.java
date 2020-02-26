package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTypeMaxPlayCountResult;
import com.zh.web.entity.VideoTypeMaxPlayCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/23 20:11
 * @Description
 */
@Api(value="video_type_max_play_count页面管理接口",description = "video_type_max_play_count页面管理接口，提供页面查询功能")
public interface VideoTypeMaxPlayCountControllerApi {

    @ApiOperation("根据video_aid查询video_type_max_play_count信息")
    @ApiImplicitParam(name="id",value = "视频aid"
            ,required=true,paramType="path",dataType="String")
    // @ApiImplicitParams
    VideoTypeMaxPlayCountResult selectOne(String aid);

    @ApiOperation("分页查询video_type_max_play_count信息")
    DataGridResult<VideoTypeMaxPlayCount> queryVideoTypeMaxPlayCounts(VideoTypeMaxPlayCount videoTypeMaxPlayCount, int page, int rows);

    @ApiOperation("查询video_type_max_play_count所有视频信息")
    DataGridResult<VideoTypeMaxPlayCount> queryAll();
}
