package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.ActiveUserCountApi;
import com.zh.web.entity.ActiveUserCount;
import com.zh.web.service.ActiveUserCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/18 14:52
 * @Description
 */
@RestController
@RequestMapping("activeUserCount")
public class ActiveUserCountController implements ActiveUserCountApi {

    @Resource
    private ActiveUserCountService activeUserCountServiceImpl;


    @GetMapping("queryDayActiveUserCount")
    @Override
    public DataGridResult<ActiveUserCount> queryDayActiveUserCount() {
        return activeUserCountServiceImpl.queryDayActiveUserCount();
    }
}
