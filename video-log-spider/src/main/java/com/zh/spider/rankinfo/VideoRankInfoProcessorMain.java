package com.zh.spider.rankinfo;

import com.zh.spider.rankinfo.entity.Constants;
import com.zh.spider.rankinfo.pipeline.FileDatePipeline;
import com.zh.spider.rankinfo.service.JobProcessorService;
import us.codecraft.webmagic.Spider;

import java.util.Arrays;


/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 19:18
 * @Description 爬取哔哩哔哩全站排行榜数据
 */
public class VideoRankInfoProcessorMain {

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            args = new String[1];
            args[0] = "C:\\Users\\Tourist\\Desktop\\video-log\\input";
           // args[0] = "video_log";
        }
        //HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //设置代理服务器
        //httpClientDownloader.setProxyProvider(SimpleProxSystemyProvider.from(new Proxy("39.137.69.8",8080)));
        String[] urls = Arrays.copyOf(Constants.VIDEO_ALL_DAY_RANKING_URL,
                Constants.VIDEO_ALL_DAY_RANKING_URL.length
                        + Constants.VIDEO_ALL_WEEK_RANKING_URL.length
                        + Constants.VIDEO_ALL_MONTH_RANKING_URL.length);

        System.arraycopy(Constants.VIDEO_ALL_WEEK_RANKING_URL, 0, urls,
                Constants.VIDEO_ALL_DAY_RANKING_URL.length
                , Constants.VIDEO_ALL_WEEK_RANKING_URL.length);

        System.arraycopy(Constants.VIDEO_ALL_MONTH_RANKING_URL, 0, urls,
                Constants.VIDEO_ALL_DAY_RANKING_URL.length + Constants.VIDEO_ALL_WEEK_RANKING_URL.length
                , Constants.VIDEO_ALL_MONTH_RANKING_URL.length);

        Spider.create(new JobProcessorService())
                //.addUrl("http://ip.chinaz.com/")
                // .setDownloader(httpClientDownloader)
                .addUrl(urls)
                .addPipeline(new FileDatePipeline(args[0]))
                //.addPipeline(new KafkaPipeline(args[0]))
                .thread(25)
                .run();
    }
}
