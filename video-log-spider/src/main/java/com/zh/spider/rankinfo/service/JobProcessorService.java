package com.zh.spider.rankinfo.service;

import com.zh.spider.rankinfo.entity.VideoInfo;
import com.zh.spider.rankinfo.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/4 21:45
 * @Description 页面处理, 数据清洗
 */
@Slf4j
public class JobProcessorService implements PageProcessor {

    @Override
    public void process(Page page) {
        //获取所有content节点
        List<Selectable> contentNodes = page.getHtml().css("div.content").nodes();

        List<VideoInfo> videoInfos = new ArrayList<>();

        for (Selectable contentNode : contentNodes) {
            //获取img节点
            Selectable imgNode = contentNode.css(".img");
            //获取视频地址
            String videoUrl = imgNode.css("a", "href").get();
            //获取info节点
            Selectable infoNode = contentNode.css(".info");

            //获取视频名
            String videoName = infoNode.css(".title", "text").get();
            //获取detail下所有节点
            List<Selectable> detailNodes = infoNode.css("div.detail span.data-box", "text").nodes();
            //获取点击量
            String playCount = MathUtils.getNumber(detailNodes.get(0).toString());
            //获取评论数
            String commentCount = MathUtils.getNumber(detailNodes.get(1).toString());
            //获取作者
            String videoAuthor = detailNodes.get(2).toString();
            //获取综合得分
            String compositeScore = infoNode.css(".pts").css("div").nodes().get(1).css("div", "text").get();

            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setVideoName(videoName);
            videoInfo.setVideoUrl(videoUrl);
            videoInfo.setPlayCount(playCount);
            videoInfo.setCommentCount(commentCount);
            videoInfo.setVideoAuthor(videoAuthor);
            videoInfo.setCompositeScore(compositeScore);

            String[] typeNumbers = MathUtils.getTypeNumber(page.getUrl().toString());
            //设置频道分类ID
            videoInfo.setVideoTypeId(typeNumbers[0]);
            //设置发布日期
            videoInfo.setPublishDate(typeNumbers[1]);
            //设置排行榜类型
            videoInfo.setRankingType(typeNumbers[2]);
            //添加进列表中
            videoInfos.add(videoInfo);
        }
        //乱序洗牌
        Collections.shuffle(videoInfos);
        page.putField("videoInfos", videoInfos);
       /* String ip = page.getHtml().css("dd.fz24","text").get();
        log.info("ip地址:[{}]",ip);*/
    }

    //爬虫配置
    private Site site = Site.me()
            .setCharset("utf-8") //按照哪种字符集进行读取
            .setTimeOut(10000)//超时时间 毫秒
            .setRetrySleepTime(3000)//重试间隔时间 毫秒
            .setRetryTimes(3);//重试次数

    @Override
    public Site getSite() {
        return site;
    }
}
