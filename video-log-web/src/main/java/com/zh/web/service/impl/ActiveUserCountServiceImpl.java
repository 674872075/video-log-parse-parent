package com.zh.web.service.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.dao.ActiveUserCountMapper;
import com.zh.web.entity.ActiveUserCount;
import com.zh.web.service.ActiveUserCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/18 14:59
 * @Description
 */

@Service
public class ActiveUserCountServiceImpl implements ActiveUserCountService {

    @Resource
    private ActiveUserCountMapper activeUserCountMapper;


    @Override
    public DataGridResult<ActiveUserCount> queryDayActiveUserCount() {
        List<ActiveUserCount> activeUserCounts = activeUserCountMapper.selectAll();
        return new DataGridResult<ActiveUserCount>(Long.valueOf(activeUserCounts.size()),activeUserCounts);
    }
}
