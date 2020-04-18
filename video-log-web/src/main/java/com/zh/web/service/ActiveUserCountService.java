package com.zh.web.service;

import com.zh.common.dto.DataGridResult;
import com.zh.web.entity.ActiveUserCount;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/18 14:58
 * @Description
 */
public interface ActiveUserCountService {

    /**
     * 查询用户日活量
     * @return
     */
    DataGridResult<ActiveUserCount> queryDayActiveUserCount();

}
