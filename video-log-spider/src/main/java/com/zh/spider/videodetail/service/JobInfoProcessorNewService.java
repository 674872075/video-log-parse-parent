package com.zh.spider.videodetail.service;

import com.jayway.jsonpath.PathNotFoundException;
import com.zh.spider.videodetail.entity.Constants;
import com.zh.spider.videodetail.entity.VideoDetails;
import com.zh.spider.videodetail.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/18 13:17
 * @Description 页面详情页处理
 */

/**
 * 个人信息接口 根据mid(用户id)查询
 * https://api.bilibili.com/x/space/acc/info?mid=270494247&jsonp=jsonp
 * <p>
 * 视频详情页 根据aid(视频id)查询
 * https://api.bilibili.com/x/web-interface/view?aid=88304073
 * <p>
 * 视频列表页
 * https://s.search.bilibili.com/cate/search?callback=jqueryCallback_bili_04516996634956216&main_ver=v3&search_type=video&view_type=hot_rank&order=click&copy_right=-1&cate_id=24&page=1
 * &pagesize=20&jsonp=jsonp&time_from=20200211&time_to=20200218&_=1582032132311
 */

//https://api.bilibili.com/x/web-interface/view?aid=88304073&cid=151930706

@Slf4j
public class JobInfoProcessorNewService implements PageProcessor {

    @Override
    public void process(Page page) {
        try {
            //列表页
            List<Selectable> resultNodes = page.getJson().removePadding("callback").jsonPath("$.result[*]").nodes();
            if (CollectionUtils.isNotEmpty(resultNodes)) {
                for (Selectable resultNode : resultNodes) {
                    Json resultJson = new Json(resultNode.get());
                    String rankScore = resultJson.jsonPath("$.rank_score").get();
                    String arcurl = resultJson.jsonPath("$.arcurl").get();
                    String pubdate = resultJson.jsonPath("$.pubdate").get();
                    String aid = resultJson.jsonPath("$.id").get();
                    //防止aid不存在
                    if (StringUtils.isNotBlank(aid)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("videoUrl", arcurl);
                        map.put("rankScore", rankScore);
                        map.put("pubdate", pubdate);
                        arcurl = Constants.URL_DETAIL_PREFIX + aid;
                        Request request = new Request(arcurl);
                        //添加浏览器标识  可一定程度上减少触发反爬机制
                        request.addHeader("user-agent", Constants.USER_AGENT);
                        //链接来源  避开防盗链设置
                        request.addHeader("referer", arcurl);
                        request.setExtras(map);
                        page.addTargetRequest(request);
                    }
                }
                //消除jsonp填充内容
                String numPages = page.getJson().removePadding("callback").jsonPath("$.numPages").get();
                String curPage = page.getJson().removePadding("callback").jsonPath("$.page").get();
                int cur_page = Integer.parseInt(curPage);
                if (cur_page < Integer.parseInt(numPages)) {
                    //翻页
                    cur_page++;
                    String pageListUrl = page.getRequest().getUrl();
                    int index = pageListUrl.lastIndexOf("&page=");
                    //防止由于不断追加新的&page=导致的url过长
                    if (index > -1) {
                        //舍去&page=
                        pageListUrl = pageListUrl.substring(0, index);
                    }
                    pageListUrl = pageListUrl + "&page=" + cur_page;
                    //添加到Scheduler队列继续发送请求
                    page.addTargetRequest(pageListUrl);
                }
            }
            //列表页不保存数据  设置为true标识此次请求不进入管道pipeline 可节省资源
            page.setSkip(true);
        } catch (IllegalStateException | PathNotFoundException e) {
            //removePadding移除回调填充失败会抛出异常IllegalStateException 此时肯定不在列表页那就是详情页了
            //jsonPath的第二级节点没找到会抛出异常PathNotFoundException 此时肯定不在列表页那就是详情页了
            //详情页
            getVideodetails(page);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("爬取数据出错：{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 视频详情页处理
     */
    private void getVideodetails(Page page) {
        Json json = page.getJson();
        String code = json.jsonPath("$.code").get();
        if (!code.equals("0")) {
            String message = json.jsonPath("$.message").get();
            if (log.isErrorEnabled()) {
                log.error("爬取视频详情时出错,错误信息: [{}],视频地址:[{}]", message, page.getRequest().getUrl());
            }
            return;
        }
        //获取视频ID
        String videoAid = json.jsonPath("$.data.aid").get();

        //获取视频名字
        String videoName = json.jsonPath("$.data.title").get();
        /**
         * 获取发布日期，具体时间
         * 例如:2020-02-12 22:00:15
         */
        //String publishDate = json.jsonPath("$.data.pubdate").get();
        String publishDate = (String) Optional.ofNullable(page.getRequest().getExtra("pubdate")).orElse("");
        /**
         * 博主ID
         */
        String videoAuthorMid = json.jsonPath("$.data.owner.mid").get();
        /**
         * 视频博主
         */
        String videoAuthor = json.jsonPath("$.data.owner.name").get();
        /**
         * 视频地址
         */
        String videoUrl = (String) Optional.ofNullable(page.getRequest().getExtra("videoUrl")).orElse("");
        /**
         * 综合得分
         */
        String rankScore = (String) Optional.ofNullable(page.getRequest().getExtra("rankScore")).orElse("");
        /**
         * 播放量
         */
        String playCount = json.jsonPath("$.data.stat.view").get();
        /**
         *  弹幕数
         */
        String danmakuCount = json.jsonPath("$.data.stat.danmaku").get();
        /**
         * 点赞数
         */
        String videoLikeCount = json.jsonPath("$.data.stat.like").get();
        /**
         * 投硬币数
         */
        String videoCoinCount = json.jsonPath("$.data.stat.coin").get();
        /**
         * 收藏人数
         */
        String videoCollectCount = json.jsonPath("$.data.stat.favorite").get();
        /**
         * 转发人数
         */
        String videoShareCount = json.jsonPath("$.data.stat.share").get();
        /**
         *  评论数
         */
        String commentCount = json.jsonPath("$.data.stat.reply").get();
        /**
         * 视频分类ID 父分类ID
         */
        String videoTypeId = json.jsonPath("$.data.tid").get();

        /**
         * 视频分类名字  详细分类名称(子分类名称)
         */
        //String videoTypeName = json.jsonPath("$.data.tname").get();

        //===========start===改成粗分类===start===========
        /**
         * 视频分类名字  父分类名称
         */
        String videoTypeName = VideoUtils.getVideoTypeNameByID(videoTypeId);
        //如果不存在就用子分类名称填充
        if (StringUtils.isBlank(videoTypeName)) {
            videoTypeName = json.jsonPath("$.data.tname").get();
        }
        //===========end===改成粗分类===end================
        VideoDetails videoDetails = new VideoDetails();
        videoDetails.setVideoAid(videoAid);
        videoDetails.setVideoName(videoName);
        videoDetails.setVideoUrl(videoUrl);
        videoDetails.setPlayCount(playCount);
        videoDetails.setDanmakuCount(danmakuCount);
        videoDetails.setCommentCount(commentCount);
        videoDetails.setVideoAuthor(videoAuthor);
        videoDetails.setVideoAuthorMid(videoAuthorMid);
        videoDetails.setVideoLikeCount(videoLikeCount);
        videoDetails.setVideoCoinCount(videoCoinCount);
        videoDetails.setVideoCollectCount(videoCollectCount);
        videoDetails.setVideoShareCount(videoShareCount);
        videoDetails.setRankScore(rankScore);
        videoDetails.setPublishDate(publishDate);
        videoDetails.setVideoTypeId(videoTypeId);
        videoDetails.setVideoTypeName(videoTypeName);
        //添加到resultItems
        page.putField("videoDetails", videoDetails);
    }

    //爬虫配置
    private Site site = Site.me()
            .setDomain("bilibili.com")//设置域名，需设置域名后，addCookie才可生效
            .setCharset("utf-8") //按照哪种字符集进行读取
            .setSleepTime(3000) //url之间的爬取间隔时间
            .setTimeOut(10000)//超时时间 毫秒
            .setRetrySleepTime(3000)//重试间隔时间 毫秒
            .setRetryTimes(3);//重试次数

    @Override
    public Site getSite() {
        return site;
    }

}
