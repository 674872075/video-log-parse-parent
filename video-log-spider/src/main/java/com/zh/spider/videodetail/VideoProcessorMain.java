package com.zh.spider.videodetail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zh.spider.videodetail.entity.Constants;
import com.zh.spider.videodetail.pipeline.FileDateNewPipeline;
import com.zh.spider.videodetail.proxyprovider.DynamicProxyProvider;
import com.zh.spider.videodetail.service.JobInfoProcessorNewService;
import com.zh.spider.videodetail.utils.DynamicIpsUtils;
import com.zh.spider.videodetail.utils.SpiderPropertiesUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
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
            args[0] = SpiderPropertiesUtils.getProperty("defaultOutPath");
        }
        Spider spider = Spider.create(new JobInfoProcessorNewService())
                .addPipeline(new FileDateNewPipeline(args[0]))
                //.addPipeline(new KafkaPipeline("video_log"))
                .addUrl(Constants.VIDEO_CATEGORY_URL)
                .thread(5);

 /*       HttpClientDownloader httpClientDownloader1 = new HttpClientDownloader();
        httpClientDownloader1.setProxyProvider(SimpleProxyProvider.from(new Proxy("125.104.229.203",40884)));
        //设置代理IP池
        spider.setDownloader(httpClientDownloader1);*/

        String enableUrlFileCache = SpiderPropertiesUtils.getProperty("EnableUrlFileCache");
        String urlFileCachePath = SpiderPropertiesUtils.getProperty("urlFileCachePath");
        boolean enableUrlFileCacheFlag = StrUtil.isNotBlank(enableUrlFileCache) && "true".compareToIgnoreCase(enableUrlFileCache) == 0 && StrUtil.isNotBlank(urlFileCachePath);
        //是否启用文件url缓存
        if (enableUrlFileCacheFlag) {
            //文件url缓存
            spider.setScheduler(new FileCacheQueueScheduler(urlFileCachePath).setDuplicateRemover(new BloomFilterDuplicateRemover(100000)));
        } else {
            //使用内存缓存url
            spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)));
        }

        //获取代理Ip池
        List<Proxy> proxyList = DynamicIpsUtils.getIpsWithRetry(3);

        //如果代理IP池列表不为空则使用代理IP池，为空则使用本机IP爬取
        if (CollUtil.isNotEmpty(proxyList)) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            httpClientDownloader.setProxyProvider(DynamicProxyProvider.from(proxyList.toArray(new Proxy[0])));
            //设置代理IP池
            spider.setDownloader(httpClientDownloader);
        }
        //启动爬虫
        spider.run();
    }
}
