package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTopCollectCountControllerApi;
import com.zh.web.dto.VideoTopCollectCountResult;
import com.zh.web.entity.VideoTopCollectCount;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopCollectCountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhouhao
 * @since 2020-03-03 11:19
 */
@Validated
@RestController
@RequestMapping("dwVideoTopCollectCount")
public class VideoTopCollectCountController implements VideoTopCollectCountControllerApi {

    @Resource
    private VideoTopCollectCountService videoTopCollectCountServiceImpl;


    @GetMapping("select_one/{id}")
    @Override
    public VideoTopCollectCountResult selectOne(@PathVariable("id") String aid) {
        return this.videoTopCollectCountServiceImpl.queryById(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTopCollectCount> queryVideoTopCollectCounts(VideoTopCollectCount videoTopCollectCount, @PathVariable("page") int page, @PathVariable("rows") int rows) {
        return videoTopCollectCountServiceImpl.queryVideoTopCollectCounts(videoTopCollectCount, page, rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTopCollectCount> queryAll() {
        return videoTopCollectCountServiceImpl.queryAll();
    }

    @GetMapping("getVideoCategoryCount")
    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        return videoTopCollectCountServiceImpl.getVideoCategoryCount();
    }

}