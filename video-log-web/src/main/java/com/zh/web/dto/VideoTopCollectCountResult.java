package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTopCollectCount;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTopCollectCountResult extends ResponseResult {

     private VideoTopCollectCount videoTopCollectCount;

     public VideoTopCollectCountResult(ResultCode resultCode, VideoTopCollectCount videoTopCollectCount){
        super(resultCode);
        this.videoTopCollectCount=videoTopCollectCount;
     }

}
