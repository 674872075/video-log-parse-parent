package com.zh.spider.videodetail.pipeline;

import com.zh.spider.videodetail.entity.VideoDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
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
        VideoDetails videoDetails = resultItems.get("videoDetails");
        if (Objects.isNull(videoDetails)) {
            return;
        }
        Properties props = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kafka-conf.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            log.error("kafka-conf.properties加载失败: [{}]", e.getMessage(), e);
        }
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(props);
            //已创建video_log主题  默认设置有1个分区  每个分区两个副本
            producer.send(new ProducerRecord<>(topic, videoDetails.toString()));
        } finally {
            producer.close();
        }

    }

}
