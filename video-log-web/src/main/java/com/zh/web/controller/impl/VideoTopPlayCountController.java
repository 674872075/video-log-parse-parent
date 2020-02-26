package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTopPlayCountControllerApi;
import com.zh.web.dto.VideoTopPlayCountResult;
import com.zh.web.entity.VideoTopPlayCount;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopPlayCountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DwVideoTopPlayCount)表控制层
 *
 * @author makejava
 * @since 2020-02-21 19:40:15
 */
@Validated
@RestController
@RequestMapping("dwVideoTopPlayCount")
public class VideoTopPlayCountController implements VideoTopPlayCountControllerApi {
    /**
     * 服务对象
     */
    @Resource
    private VideoTopPlayCountService videoTopPlayCountService;


    @GetMapping("select_one/{id}")
    @Override
    public VideoTopPlayCountResult selectOne(@PathVariable("id") String aid) {
        return this.videoTopPlayCountService.queryById(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTopPlayCount> queryVideoTopPlayCounts(VideoTopPlayCount videoTopPlayCount, @PathVariable("page") int page, @PathVariable("rows") int rows) {
        return videoTopPlayCountService.queryVideoTopPlayCounts(videoTopPlayCount, page, rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTopPlayCount> queryAll() {
        return videoTopPlayCountService.queryAll();
    }

    @GetMapping("getVideoCategoryCount")
    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        return videoTopPlayCountService.getVideoCategoryCount();
    }

}