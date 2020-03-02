package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.DayVideoTypeCountControllerApi;
import com.zh.web.entity.DayVideoTypeCount;
import com.zh.web.service.DayVideoTypeCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/1 16:12
 * @Description
 */
@RestController
@RequestMapping("dayVideoTypeCount")
public class DayVideoTypeCountController implements DayVideoTypeCountControllerApi {

    @Resource
    private DayVideoTypeCountService dayVideoTypeCountServiceImpl;

    @GetMapping("getALLPublishDate")
    @Override
    public List<DayVideoTypeCount> getALLPublishDate() {
        return dayVideoTypeCountServiceImpl.getALLPublishDate();
    }

    @GetMapping("queryAllByPublishDate/{publishDate}")
    @Override
    public DataGridResult<DayVideoTypeCount> queryAllByPublishDate(@PathVariable String publishDate) {
        return dayVideoTypeCountServiceImpl.queryAllByPublishDate(publishDate);
    }
}
