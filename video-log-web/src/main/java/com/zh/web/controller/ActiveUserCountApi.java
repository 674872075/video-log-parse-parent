package com.zh.web.controller;

import com.zh.common.dto.DataGridResult;
import com.zh.web.entity.ActiveUserCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/18 14:45
 * @Description
 */
@Api(value="active_user_count页面管理接口",description = "active_user_count页面管理接口，提供页面查询功能")
public interface ActiveUserCountApi {

    @ApiOperation("查询用户日活量")
    DataGridResult<ActiveUserCount> queryDayActiveUserCount();

}
