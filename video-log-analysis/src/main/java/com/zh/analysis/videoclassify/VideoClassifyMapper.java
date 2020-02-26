package com.zh.analysis.videoclassify;

import com.zh.analysis.pojo.Fileds;
import com.zh.analysis.pojo.VideoDetails;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class VideoClassifyMapper extends Mapper<LongWritable, Text, VideoDetails,Text> {


    private VideoDetails videoDetails = new VideoDetails();

    private Text text=new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\001");
        videoDetails.setVideoAid(split[Fileds.VIDEO_AID]);
        videoDetails.setVideoName(split[Fileds.VIDEO_NAME]);
        videoDetails.setVideoUrl(split[Fileds.VIDEO_URL]);
        videoDetails.setPlayCount(split[Fileds.PLAY_COUNT]);
        videoDetails.setDanmakuCount(split[Fileds.DANMAKU_COUNT]);
        videoDetails.setCommentCount(split[Fileds.COMMENT_COUNT]);
        videoDetails.setVideoAuthor(split[Fileds.VIDEO_AUTHOR]);
        videoDetails.setVideoAuthorMid(split[Fileds.VIDEO_AUTHORMID]);
        videoDetails.setVideoLikeCount(split[Fileds.VIDEO_LIKECOUNT]);
        videoDetails.setVideoCoinCount(split[Fileds.VIDEO_COINCOUNT]);
        videoDetails.setVideoCollectCount(split[Fileds.VIDEO_COLLECTCOUNT]);
        videoDetails.setVideoShareCount(split[Fileds.VIDEO_SHARECOUNT]);
        videoDetails.setRankScore(split[Fileds.RANK_SCORE]);
        videoDetails.setPublishDate(split[Fileds.PUBLISH_DATE]);
        videoDetails.setVideoTypeId(split[Fileds.VIDEO_TYPEID]);
        videoDetails.setVideoTypeName(split[Fileds.VIDEO_TYPENAME]);
        text.set(videoDetails.getVideoName());
        context.write(videoDetails,text);
    }


}
