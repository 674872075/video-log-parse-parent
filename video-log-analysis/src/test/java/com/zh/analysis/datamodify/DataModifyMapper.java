package com.zh.analysis.datamodify;


import com.zh.analysis.pojo.Fileds;
import com.zh.analysis.pojo.VideoDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DataLaunderMapper 读取日志文件
 */
@Slf4j
class DataModifyMapper extends Mapper<LongWritable, Text, NullWritable, VideoDetails> {

    //定义MultipleOutputs
    private MultipleOutputs<NullWritable, VideoDetails> multipleOutputs = null;

    private VideoDetails videoDetails = new VideoDetails();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //初始化MultipleOutputs对象
        multipleOutputs = new MultipleOutputs<NullWritable, VideoDetails>(context);

    }

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
        String videoTypeName = VideoUtils.getVideoTypeNameByID(split[Fileds.VIDEO_TYPEID]);
        if (StringUtils.isBlank(videoTypeName)) {
            videoTypeName = split[Fileds.VIDEO_TYPENAME];
        }
        videoDetails.setVideoTypeName(videoTypeName);
        //mapper多文件输出
        multipleOutputs.write(NullWritable.get(), videoDetails, generateFileName(videoDetails));
        //单独输出
        //context.write(NullWritable.get(), videoDetails);
    }

    /**
     * 按月份输出
     *
     * @param videoDetails
     * @return
     */
    private String generateFileName(VideoDetails videoDetails) {
        String path;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(videoDetails.getPublishDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String pubdate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            path = "access.log." + pubdate;
        } catch (Exception e) {
            log.error("数据日期格式错误,videoDetails:[{}]", videoDetails, e.getMessage());
            path = "erro_data/" + "erro_date_format";
        }
        return path;
    }

    /**
     * 用完MultipleOutputs之后一定要关闭
     */
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}