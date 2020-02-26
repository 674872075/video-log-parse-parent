package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTypeCountControllerApi;
import com.zh.web.dto.VideoTypeCountResult;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTypeCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhouhao
 * @since 2020-02-21 19:40:16
 */
@RestController
@RequestMapping("dwVideoTypeCount")
public class VideoTypeCountController implements VideoTypeCountControllerApi {

    @Resource
    private VideoTypeCountService videoTypeCountService;

    @GetMapping("select_one/{id}")
    @Override
    public VideoTypeCountResult selectOne(@PathVariable("id") String aid) {
        return this.videoTypeCountService.selectOne(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTypeCount> queryVideoTypeCounts(VideoTypeCount VideoTypeCount, @PathVariable("page") int page, @PathVariable("rows") int rows) {
        return videoTypeCountService.queryVideoTypeCounts(VideoTypeCount, page, rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTypeCount> queryAll() {
        return videoTypeCountService.queryAll();
    }

}