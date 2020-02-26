package com.zh.spider.videodetail.service;

import com.zh.spider.videodetail.entity.VideoDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/18 13:17
 * @Description 页面详情页处理
 */
//https://api.bilibili.com/x/web-interface/view?aid=88304073&cid=151930706
//https://api.bilibili.com/x/web-interface/view?aid=88304073
@Slf4j
public class JobInfoProcessorService implements PageProcessor {

    @Override
    public void process(Page page) {
        List<String> videoDetailsUrls = page.getHtml().css("#videolist_box .r a.title").links().all();
        //详情页
        if (CollectionUtils.isEmpty(videoDetailsUrls)) {
            getVideodetails(page);
        } else {
            //列表页
            page.addTargetRequests(videoDetailsUrls);
            String url = page.getUrl().get();
            int index = url.lastIndexOf('/');
            String newUrl = url.substring(0, index - 1);
            String datestr = url.substring(index);
            int pageNum = Integer.parseInt(url.substring(index - 1, index));
            //总页数
            int sumCount = Integer.parseInt(page.getHtml().css("#videolist_box li.page-item.last button", "text").get());
            //小于总页数继续爬取
            //  if (pageNum < sumCount) {
            if (pageNum < 5) {
                pageNum = pageNum + 1;
                newUrl = newUrl + pageNum + datestr;
                page.addTargetRequest(newUrl);
            }
        }
    }

    private void getVideodetails(Page page) {
        //获取所有viewbox_report节点 头部信息
        Selectable viewboxNode = page.getHtml().css("#viewbox_report");
        //获取视频名字
        String videoName = viewboxNode.css("h1", "title").get();
        List<Selectable> videoDataNode = viewboxNode.css(".video-data").nodes();
        Selectable videoData1 = videoDataNode.get(0);
        Selectable videoData2 = videoDataNode.get(1);
        List<Selectable> spanNodes = videoData1.css("span").nodes();
        /**
         * 获取视频类型
         */
        //TODO 还需将汉字转化为Id
        String videoTypeId = spanNodes.get(0).css("a", "text").get();
        /**
         * 获取发布日期，具体时间
         * 例如:2020-02-12 22:00:15
         */
        String publishDate = spanNodes.get(1).css("span", "text").get();
        String viewText = videoData2.css("span.view", "text").get();
        String dmText = videoData2.css("span.dm", "text").get();
        //播放量
        String playCount = viewText.substring(0, viewText.indexOf("万"));
        //评论数
        String commentCount = viewText.substring(0, viewText.indexOf("万"));
        //视频博主
        String videoAuthor = page.getHtml().css("#v_upinfo a.username.is-vip", "text").get();
        Selectable opsNode = page.getHtml().css("#arc_toolbar_report > div.ops");
        //点赞数
        String videoLikeCount = opsNode.css("span.like", "text").get();
        //投硬币数
        String videoCoinCount = opsNode.css("span.coin", "text").get();
        //收藏人数
        String videoCollectCount = opsNode.css("span.collect", "text").get();
        //转发人数
        String videoShareCount = opsNode.css("span.share", "text").get();
        VideoDetails videoDetails = new VideoDetails();
        videoDetails.setVideoName(videoName);
        videoDetails.setVideoUrl(page.getRequest().getUrl());
        videoDetails.setVideoTypeId(videoTypeId);
        videoDetails.setPublishDate(publishDate);
        videoDetails.setPlayCount(playCount);
        videoDetails.setCommentCount(commentCount);
        videoDetails.setVideoCoinCount(videoCoinCount);
        videoDetails.setVideoCollectCount(videoCollectCount);
        videoDetails.setVideoShareCount(videoShareCount);
        videoDetails.setVideoAuthor(videoAuthor);
        videoDetails.setVideoLikeCount(videoLikeCount);
        page.putField("videoDetails", videoDetails);
    }

    //爬虫配置
    private Site site = Site.me()
            .setDomain("bilibili.com")//设置域名，需设置域名后，addCookie才可生效
            .setCharset("utf-8") //按照哪种字符集进行读取
            .setTimeOut(10000)//超时时间 毫秒
            .setRetrySleepTime(3000)//重试间隔时间 毫秒
            .setRetryTimes(3);//重试次数

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.setProperty("selenuim_config", "F:/space/video-log-parse-parent/video-log-spider/src/main/resources/selenium-config.ini");
        Spider.create(new JobInfoProcessorService())
               // .setDownloader(new SeleniumDownloader("C:/Users/Tourist/Desktop/video-log/driver/chromedriver.exe"))
                .addPipeline(new FilePipeline("C:/Users/Tourist/Desktop/video-log/newinput"))
                .addUrl("https://www.bilibili.com/v/douga/mad/?spm_id_from=333.5.b_7375626e6176.2#/all/click/0/1/2020-02-11,2020-02-18")
                // .thread(5)
                .run();
    }
}
