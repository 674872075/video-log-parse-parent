package com.zh.spider.videodetail;

import com.zh.spider.videodetail.entity.Constants;
import com.zh.spider.videodetail.pipeline.FileDateNewPipeline;
import com.zh.spider.videodetail.service.JobInfoProcessorNewService;
import com.zh.spider.videodetail.utils.DynamicIpsUtils;
import com.zh.spider.videodetail.utils.PropertiesUtils;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 19:18
 * @Description 爬取哔哩哔哩各分类视频详情数据
 */
public class VideoProcessorMain {

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            args = new String[1];
            args[0] = PropertiesUtils.getProperty("defaultOutPath");
        }
        Spider spider = Spider.create(new JobInfoProcessorNewService())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                // .setScheduler(new FileCacheQueueScheduler(PropertiesUtils.getProperty("urlFileCachePath")).setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(new FileDateNewPipeline(args[0]))
                .addUrl(Constants.VIDEO_CATEGORY_URL)
                .thread(25);
        //获取代理Ip池
        List<Proxy> proxyList = DynamicIpsUtils.getIps();
        if (CollectionUtils.isNotEmpty(proxyList)) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxyList.toArray(new Proxy[0])));
            //设置代理IP池
            spider.setDownloader(httpClientDownloader);
        }
        //启动爬虫
        spider.run();
    }
}
