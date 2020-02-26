package com.zh.spider.rankinfo.pipeline;

import com.zh.spider.rankinfo.entity.VideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 15:49
 * @Description
 * 用于持久化数据
 */
@Slf4j
public class FileDatePipeline implements Pipeline {

    private static final String PATH_SEPERATOR = "/";

    private String path;

    public  FileDatePipeline(String path){
        this.path=path;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<VideoInfo> videoInfos = resultItems.get("videoInfos");
        if(CollectionUtils.isEmpty(videoInfos)){
            return;
        }
        VideoInfo info = videoInfos.get(0);
        //目录分开输出
        //String path = this.path + PATH_SEPERATOR + nowStr+"_"+info.getRankingType() + PATH_SEPERATOR;
        String path = this.path + PATH_SEPERATOR;
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        BufferedWriter bw=null;
        try {
            bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path
                    +"access.log."
                    +nowStr
                    +"_0" //追加到一个文件里面
                    //+info.getVideoTypeId()  //根据分类存放到不同的文件
                    +"_"
                    +info.getPublishDate()
                    +"_"
                    +info.getRankingType()
                    ,true), "utf-8"));
            for (VideoInfo videoInfo : videoInfos) {
                bw.write(videoInfo.toString());
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            log.warn("write file error", e);
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                log.warn("io close error", e);
            }
        }
    }

   /* public static void main(String[] args) {
      Instant instant=Instant.ofEpochMilli(Long.parseLong("1581825622000"));
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        String nowStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(nowStr);
    }*/
}
