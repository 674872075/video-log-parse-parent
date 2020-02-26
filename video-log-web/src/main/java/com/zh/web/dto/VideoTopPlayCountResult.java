package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTopPlayCount;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTopPlayCountResult extends ResponseResult {

     private VideoTopPlayCount videoTopPlayCount;

     public VideoTopPlayCountResult(ResultCode resultCode, VideoTopPlayCount videoTopPlayCount){
        super(resultCode);
        this.videoTopPlayCount=videoTopPlayCount;
     }

}
