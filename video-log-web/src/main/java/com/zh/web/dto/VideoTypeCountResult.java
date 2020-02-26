package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTypeCount;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTypeCountResult extends ResponseResult {

     private VideoTypeCount videoTypeCount;

     public VideoTypeCountResult(ResultCode resultCode, VideoTypeCount videoTypeCount){
        super(resultCode);
        this.videoTypeCount=videoTypeCount;
     }

}
