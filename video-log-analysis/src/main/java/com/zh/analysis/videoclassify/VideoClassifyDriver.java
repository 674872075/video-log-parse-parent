package com.zh.analysis.videoclassify;

import com.zh.analysis.pojo.VideoDetails;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import java.net.URI;

public class VideoClassifyDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(super.getConf());
        job.setJarByClass(VideoClassifyDriver.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[1] + "/normal"));

        job.setMapperClass(VideoClassifyMapper.class);
        job.setMapOutputKeyClass(VideoDetails.class);
        job.setMapOutputValueClass(Text.class);

        //分区
//        job.setPartitionerClass(VideoPartitioner.class);

        job.setReducerClass(VideoClassifyReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(VideoDetails.class);
        //      job.setNumReduceTasks(2);
        args[1] = args[1] + "/normal_video_category";
        //FileSystem fileSystem = FileSystem.getLocal(super.getConf());
        FileSystem fileSystem=FileSystem.get(new URI("hdfs://node01:8020"), super.getConf(), "root");
        if (fileSystem.exists(new Path(args[1]))) {
            fileSystem.delete(new Path(args[1]), true);
        }

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

   /* public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            args = new String[2];
            args[0] = "file:///C:/Users/Tourist/Desktop/video-log/newinput/2020-02-19";
            args[1] = "file:///C:/Users/Tourist/Desktop/video-log/newinput/2020-02-19/output";
        }
        Configuration conf = new Configuration();
        System.out.println("开始视频日志分类.....");
        ToolRunner.run(conf,new VideoClassifyDriver(),args);
        System.out.println("视频日志分类完成.....");
    }*/
}
