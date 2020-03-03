package com.zh.web.dto;

import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import com.zh.web.entity.VideoTopRankScore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 */
@Data
@NoArgsConstructor
public class VideoTopRankScoreResult extends ResponseResult {

     private VideoTopRankScore videoTopRankScore;

     public VideoTopRankScoreResult(ResultCode resultCode, VideoTopRankScore videoTopRankScore){
        super(resultCode);
        this.videoTopRankScore=videoTopRankScore;
     }

}
