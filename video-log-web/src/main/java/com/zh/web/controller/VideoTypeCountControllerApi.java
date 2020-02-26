package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dto.VideoTypeCountResult;
import com.zh.web.entity.VideoTypeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
@Api(value="video_type_count页面管理接口",description = "video_type_count页面管理接口，提供页面查询功能")
public interface VideoTypeCountControllerApi {

    @ApiOperation("根据video_type_id查询video_type_count信息")
    @ApiImplicitParam(name="id",value = "视频aid"
            ,required=true,paramType="path",dataType="String")
        // @ApiImplicitParams
    VideoTypeCountResult selectOne(String videoTypeId);

    @ApiOperation("分页查询video_type_count信息")
    DataGridResult<VideoTypeCount> queryVideoTypeCounts(VideoTypeCount videoTypeCount, int page, int rows);

    @ApiOperation("查询video_type_count所有视频信息")
    DataGridResult<VideoTypeCount> queryAll();


}