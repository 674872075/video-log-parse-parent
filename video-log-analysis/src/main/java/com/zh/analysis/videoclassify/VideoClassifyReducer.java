package com.zh.analysis.videoclassify;

import com.zh.analysis.pojo.VideoDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class VideoClassifyReducer extends Reducer<VideoDetails, Text, NullWritable, VideoDetails> {

    //定义MultipleOutputs
    private MultipleOutputs<NullWritable, VideoDetails> multipleOutputs = null;

    @Override
    protected void setup(Reducer.Context context) throws IOException, InterruptedException {
        //初始化MultipleOutputs对象
        multipleOutputs = new MultipleOutputs<NullWritable, VideoDetails>(context);
    }

    @Override
    protected void reduce(VideoDetails key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        multipleOutputs.write(NullWritable.get(), key, generateFileName(key));
    }

    private String generateFileName(VideoDetails videoDetails) {
        String path;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(videoDetails.getPublishDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String pubdate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime nowlocalDateTime = LocalDateTime.now();
            String dirPubdate = nowlocalDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            path ="access_log_"+ pubdate;
        } catch (Exception e) {
            log.error("数据日期格式错误,videoDetails:[{}]", videoDetails, e.getMessage());
            path = "erro_data/"+"erro_date_format";
        }
        return path;
    }

    /**
     * 用完MultipleOutputs之后一定要关闭
     */
    @Override
    protected void cleanup(Reducer.Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}
