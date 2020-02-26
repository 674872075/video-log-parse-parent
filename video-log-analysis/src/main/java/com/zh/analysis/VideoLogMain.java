package com.zh.analysis;

import com.zh.analysis.dataclean.DataCleanDriver;
import com.zh.analysis.videoclassify.VideoClassifyDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VideoLogMain {

    public static void main(String[] args) throws Exception {

        if (args == null || args.length < 2) {
            args = new String[2];
            LocalDateTime now = LocalDateTime.now();
            String datestr = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            args[0] = "hdfs://node01:8020/videologs/logs/input/"+datestr;
            args[1] = "hdfs://node01:8020/videologs/logs/output/"+datestr;
        }
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "2");
        System.out.println("数据开始清洗.......");
        ToolRunner.run(conf,new DataCleanDriver(),args);
        System.out.println("数据清洗完成........");
        System.out.println("开始视频日志分类.....");
        ToolRunner.run(conf,new VideoClassifyDriver(),args);
        System.out.println("视频日志分类完成.....");
    }
}
