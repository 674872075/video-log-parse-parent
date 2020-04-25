package com.zh.spider.videodetail;

import com.zh.spider.videodetail.entity.Constants;
import com.zh.spider.videodetail.pipeline.FileDateNewPipeline;
import com.zh.spider.videodetail.service.JobInfoProcessorNewService;
import com.zh.spider.videodetail.utils.PropertiesUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 19:18
 * @Description 爬取哔哩哔哩全站排行榜数据
 */
public class VideoProcessorMain {

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            args = new String[1];
            args[0] = PropertiesUtils.getProperty("defaultOutPath");
        }
        //设置代理IP池
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("60.191.11.229", 3128),
                new Proxy("36.25.243.51", 80),
                new Proxy("121.40.141.226", 31280)));*/
        Spider.create(new JobInfoProcessorNewService())
                //.setDownloader(httpClientDownloader)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(new FileDateNewPipeline(args[0]))
                .addUrl(Constants.VIDEO_CATEGORY_URL)
                .thread(18)
                .run();
    }
}
