package com.zh.web.controller.impl;

import com.zh.common.dto.DataGridResult;
import com.zh.web.controller.VideoTopDanmakuControllerApi;
import com.zh.web.dto.VideoTopDanmakuResult;
import com.zh.web.entity.VideoTopDanmaku;
import com.zh.web.entity.VideoTypeCount;
import com.zh.web.service.VideoTopDanmakuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/3/3 18:15
 * @Description
 */
@Validated
@RestController
@RequestMapping("dwVideoTopDanmaku")
public class VideoTopDanmakuController implements VideoTopDanmakuControllerApi {

    @Resource
    private VideoTopDanmakuService videoTopDanmakuServiceImpl;

    @GetMapping("select_one/{id}")
    @Override
    public VideoTopDanmakuResult selectOne(@PathVariable("id") String aid) {
        return videoTopDanmakuServiceImpl.queryById(aid);
    }

    @PostMapping("querys/{page}/{rows}")
    @Override
    public DataGridResult<VideoTopDanmaku> queryVideoTopDanmakus(VideoTopDanmaku videoTopDanmaku, @PathVariable("page") int page,  @PathVariable("rows") int rows) {
        return videoTopDanmakuServiceImpl.queryVideoTopDanmakus(videoTopDanmaku,page,rows);
    }

    @GetMapping("queryAll")
    @Override
    public DataGridResult<VideoTopDanmaku> queryAll() {
        return videoTopDanmakuServiceImpl.queryAll();
    }

    @GetMapping("getVideoCategoryCount")
    @Override
    public DataGridResult<VideoTypeCount> getVideoCategoryCount() {
        return videoTopDanmakuServiceImpl.getVideoCategoryCount();
    }
}
