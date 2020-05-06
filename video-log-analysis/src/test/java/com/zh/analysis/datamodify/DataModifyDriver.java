package com.zh.analysis.datamodify;

import com.zh.analysis.pojo.VideoDetails;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 视频数据类型名称修改(修改为粗分类)
 */
public class DataModifyDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        Job job = Job.getInstance(super.getConf());
        //封装驱动类
        job.setJarByClass(DataModifyDriver.class);
        //mappers
        job.setMapperClass(DataModifyMapper.class);
        //设置mapper输出
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(VideoDetails.class);
        //设置reducer个数为0个
        // reduce个数和分区个数相关 mapper个数和split个数相关 一个文件不管多小会有一个InputSplit对应
        //job.setNumReduceTasks(0);
        //设置数据源
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));

        FileSystem fileSystem = FileSystem.getLocal(super.getConf());
        //FileSystem fileSystem=FileSystem.get(new URI("hdfs://node01:8020"), super.getConf(), "root");
        if (fileSystem.exists(new Path(args[1]))) {
            fileSystem.delete(new Path(args[1]), true);
        }

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            args = new String[2];
            args[0] = "C:\\Users\\Tourist\\Desktop\\video-log\\newinput";
            args[1] = "C:\\Users\\Tourist\\Desktop\\video-log\\newinput\\modify";
        }
        Configuration conf = new Configuration();
        System.out.println("开始进行视频分类名称修改.......");
        ToolRunner.run(conf, new DataModifyDriver(), args);
        System.out.println("视频分类名称修改完成........");

    }
}
