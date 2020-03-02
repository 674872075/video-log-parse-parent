package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.entity.DayVideoTypeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
@Api(value="day_video_type_count页面管理接口",description = "day_video_type_count页面管理接口，提供页面查询功能")
public interface DayVideoTypeCountControllerApi {


    @ApiOperation("查询day_video_type_count所有所有日期")
    List<DayVideoTypeCount> getALLPublishDate();

    @ApiOperation("根据发布日期查询所有分类视频个数")
    @ApiImplicitParam(name="publishDate",value = "发布日期"
            ,required=true,paramType="path",dataType="String")
        // @ApiImplicitParams
    DataGridResult<DayVideoTypeCount> queryAllByPublishDate(String publishDate);

}