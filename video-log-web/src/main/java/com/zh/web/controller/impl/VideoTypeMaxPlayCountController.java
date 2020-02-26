package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTypeMaxPlayCountControllerApi;
import com.zh.web.dto.VideoTypeMaxPlayCountResult;
import com.zh.web.entity.VideoTypeMaxPlayCount;
import com.zh.web.service.VideoTypeMaxPlayCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DwVideoTypeMaxPlayCount)表控制层
 * @author makejava
 * @since 2020-02-21 19:40:16
 */
@RestController
@RequestMapping("video_type_max_play_count")
public class VideoTypeMaxPlayCountController implements VideoTypeMaxPlayCountControllerApi {

    @Resource
    private VideoTypeMaxPlayCountService videoTypeMaxPlayCountService;

    @GetMapping("select_one/{id}")
    @Override
    public VideoTypeMaxPlayCountResult selectOne(@PathVariable("id") String aid) {
        return this.videoTypeMaxPlayCountService.queryById(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTypeMaxPlayCount> queryVideoTypeMaxPlayCounts(VideoTypeMaxPlayCount videoTypeMaxPlayCount,@PathVariable("page") int page,@PathVariable("rows") int rows) {
        return videoTypeMaxPlayCountService.queryVideoTypeMaxPlayCounts(videoTypeMaxPlayCount, page, rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTypeMaxPlayCount> queryAll() {
        return videoTypeMaxPlayCountService.queryAll();
    }

}