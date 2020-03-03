package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTopDanmaku;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTopDanmakuResult extends ResponseResult {

     private VideoTopDanmaku videoTopDanmaku;

     public VideoTopDanmakuResult(ResultCode resultCode, VideoTopDanmaku videoTopDanmaku){
        super(resultCode);
        this.videoTopDanmaku=videoTopDanmaku;
     }

}
