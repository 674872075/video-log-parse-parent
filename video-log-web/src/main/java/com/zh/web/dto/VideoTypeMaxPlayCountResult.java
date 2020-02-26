package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTypeMaxPlayCount;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTypeMaxPlayCountResult extends ResponseResult {

     private VideoTypeMaxPlayCount videoTypeMaxPlayCount;

     public  VideoTypeMaxPlayCountResult(ResultCode resultCode, VideoTypeMaxPlayCount videoTypeMaxPlayCount){
        super(resultCode);
        this.videoTypeMaxPlayCount=videoTypeMaxPlayCount;
     }

}
