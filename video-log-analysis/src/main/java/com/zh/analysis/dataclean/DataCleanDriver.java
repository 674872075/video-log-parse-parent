package com.zh.analysis.dataclean;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;

import java.net.URI;

/**
 * 数据清洗
 */
public class DataCleanDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        Job job = Job.getInstance(super.getConf());
        //封装驱动类
        job.setJarByClass(DataCleanDriver.class);
        //mappers
        job.setMapperClass(DataCleanMapper.class);
        //设置mapper输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //设置reducer个数为0个
        // reduce个数和分区个数相关 mapper个数和split个数相关
        //job.setNumReduceTasks(0);
        //设置数据源
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));

        //FileSystem fileSystem = FileSystem.getLocal(super.getConf());
        FileSystem fileSystem=FileSystem.get(new URI("hdfs://node01:8020"), super.getConf(), "root");
        if (fileSystem.exists(new Path(args[1]))) {
            fileSystem.delete(new Path(args[1]), true);
        }

        //设置处理后数据输出
        job.setOutputFormatClass(DataCleanOutFormat.class);
        //设置输出路径(最后需要输出一个_SUCCESS的成功标志的文件)
        DataCleanOutFormat.setOutputPath(job, new Path(args[1]));
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

   /* public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            args = new String[2];
            args[0] = "C:\\Users\\Tourist\\Desktop\\video-log\\newinput\\2020-02-19";
            args[1] = "C:\\Users\\Tourist\\Desktop\\video-log\\newinput\\2020-02-19\\output";
        }
        Configuration conf = new Configuration();
        System.out.println("数据开始清洗.......");
        ToolRunner.run(conf,new DataCleanDriver(),args);
        System.out.println("数据清洗完成........");

    }*/
}
