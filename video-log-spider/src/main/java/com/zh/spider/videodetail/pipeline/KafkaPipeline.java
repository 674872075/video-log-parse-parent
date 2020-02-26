package com.zh.spider.videodetail.pipeline;

import com.zh.spider.rankinfo.entity.VideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/17 14:47
 * @Description 将爬取到的视频数据信息存储到kafka
 */
@Slf4j
public class KafkaPipeline implements Pipeline {

    /**
     * 发布的主题
     */
    private String topic;

    public KafkaPipeline(String topic) {
        this.topic = topic;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<VideoInfo> videoInfos = resultItems.get("videoInfos");
        if (CollectionUtils.isEmpty(videoInfos)) {
            return;
        }
        VideoInfo info = videoInfos.get(0);
        Properties props = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kafka-conf.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
           log.error("resource properties file read fail: [{}]",e.getMessage(),e);
        }
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(props);
            for (VideoInfo videoInfo : videoInfos) {
                //已创建video_log主题  设置有3个分区  每个分区两个副本
                //设置路由key和对应的数据  通过key取上hashCode进行分区
                producer.send(new ProducerRecord<>(topic, videoInfo.getRankingType(), videoInfo.toString()));
            }
        } finally {
            producer.close();
        }

    }

}
