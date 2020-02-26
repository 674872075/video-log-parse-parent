package com.zh.analysis.dataclean;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class DataCleanRecordWriter extends RecordWriter {
    FSDataOutputStream qualified = null;
    FSDataOutputStream unqualified = null;

    public DataCleanRecordWriter(TaskAttemptContext job,Path path) {
        // 1 获取文件系统
        FileSystem fs;
        try {
            //fs = FileSystem.get(job.getConfiguration());
            fs = FileSystem.get(new URI("hdfs://node01:8020"), job.getConfiguration(), "root");
            // 2 创建输出文件路径
            Path qualifiedPath = new Path(path.toString()+"/normal/video_normal_log.txt");
            Path otherPath = new Path(path.toString()+"/dirty/video_dirty_log.txt");
            // 3 创建输出流
            qualified = fs.create(qualifiedPath);
            unqualified = fs.create(otherPath);
        } catch (Exception e) {
           log.error("DataCleanRecordWriter write fail:[{}]",e.getMessage(),e);
        }
    }

    @Override
    public void write(Object o, Object o2) throws IOException, InterruptedException {
        //判断输出数据
        boolean result = ParseLogData.parseLog(o.toString());
        if (!result){
            //非法数据
            unqualified.write((o+"\n").toString().replace("+", "").getBytes());
        }else {
            //合法数据
            qualified.write((o+"\n").toString().replace("+", "").getBytes());
        }
    }
    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        // 关闭资源
        IOUtils.closeStream(qualified);
        IOUtils.closeStream(unqualified);
    }
}
