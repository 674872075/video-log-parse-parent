package com.zh.spider.videodetail.pipeline;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.zh.spider.videodetail.entity.VideoDetails;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/19 00:25
 * @Description 用于持久化数据
 */
@Slf4j
public class FileDateNewPipeline implements Pipeline {

    private static final String PATH_SEPERATOR = "/";

    private String path;

    public FileDateNewPipeline(String path) {
        this.path = path;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        VideoDetails videoDetails = resultItems.get("videoDetails");
        //非空判断 防止空指针
        if (Objects.isNull(videoDetails)) {
            return;
        }
        String nowStr = DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN);
        try {
            Files.write(Paths.get(this.path + PATH_SEPERATOR + "access.log." + nowStr),
                    Lists.newArrayList(videoDetails.toString()),
                    StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        } catch (Exception e) {
            log.error("文件写入错误: [{}]", e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
       /* LocalDateTime now = LocalDateTime.parse("1581825622");
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));*/
        long pubDate = 1581825622; //秒
        Date date = new Date(pubDate * 1000); //转成毫秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        String nowStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
*/
        System.out.println(simpleDateFormat.format(date));
    }
}
