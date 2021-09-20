package com.zh.spider.videodetail.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jayway.jsonpath.PathNotFoundException;
import com.zh.spider.videodetail.entity.Constants;
import com.zh.spider.videodetail.entity.VideoDetails;
import com.zh.spider.videodetail.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
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
            if (CollUtil.isNotEmpty(resultNodes)) {
                resultNodes.forEach(resultNode -> {
                    Json resultJson = new Json(resultNode.get());
                    String rankScore = resultJson.jsonPath("$.rank_score").get();
                    String arcurl = resultJson.jsonPath("$.arcurl").get();
                    String pubdate = resultJson.jsonPath("$.pubdate").get();
                    String aid = resultJson.jsonPath("$.id").get();
                    //防止aid不存在
                    if (StrUtil.isNotBlank(aid)) {
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
                });
                //消除jsonp填充内容 总页数
                String totalPage = page.getJson().removePadding("callback").jsonPath("$.numPages").get();
                //当前页
                String curPage = page.getJson().removePadding("callback").jsonPath("$.page").get();
                int cur_page = NumberUtil.parseInt(curPage);
                if (cur_page < NumberUtil.parseInt(totalPage)) {
                    //翻页
                    cur_page++;
                    String pageListUrl = page.getRequest().getUrl();
                    int index = StrUtil.lastIndexOfIgnoreCase(pageListUrl,"&page=");
                    //防止由于不断追加新的&page=导致的url过长
                    if (index > -1) {
                        //舍去&page=
                        pageListUrl = StrUtil.sub(pageListUrl,0,index);
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
            log.error("爬取数据出错：{}", e.getMessage(), e);
        }
    }

    /**
     * 视频详情页处理
     */
    private void getVideodetails(Page page) {
        Json json = page.getJson();
        String code = json.jsonPath("$.code").get();
        if (!StrUtil.equals("0",code)) {
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
        String publishDate = Optional.ofNullable((String)page.getRequest().getExtra("pubdate"))
                .orElse(StrUtil.EMPTY);
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
        String videoUrl =  Optional.ofNullable((String)page.getRequest().getExtra("videoUrl"))
                .orElse(StrUtil.EMPTY);
        /**
         * 综合得分
         */
        String rankScore = Optional.ofNullable((String)page.getRequest().getExtra("rankScore"))
                .orElse(StrUtil.EMPTY);
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
        if (StrUtil.isBlank(videoTypeName)) {
            videoTypeName = json.jsonPath("$.data.tname").get();
        }
        //===========end===改成粗分类===end================
        VideoDetails videoDetails = new VideoDetails();
        videoDetails.setVideoAid(StrUtil.trimToEmpty(videoAid));
        videoDetails.setVideoName(StrUtil.trimToEmpty(videoName));
        videoDetails.setVideoUrl(StrUtil.trimToEmpty(videoUrl));
        videoDetails.setPlayCount(StrUtil.trimToEmpty(playCount));
        videoDetails.setDanmakuCount(StrUtil.trimToEmpty(danmakuCount));
        videoDetails.setCommentCount(StrUtil.trimToEmpty(commentCount));
        videoDetails.setVideoAuthor(StrUtil.trimToEmpty(videoAuthor));
        videoDetails.setVideoAuthorMid(StrUtil.trimToEmpty(videoAuthorMid));
        videoDetails.setVideoLikeCount(StrUtil.trimToEmpty(videoLikeCount));
        videoDetails.setVideoCoinCount(StrUtil.trimToEmpty(videoCoinCount));
        videoDetails.setVideoCollectCount(StrUtil.trimToEmpty(videoCollectCount));
        videoDetails.setVideoShareCount(StrUtil.trimToEmpty(videoShareCount));
        videoDetails.setRankScore(StrUtil.trimToEmpty(rankScore));
        videoDetails.setPublishDate(StrUtil.trimToEmpty(publishDate));
        videoDetails.setVideoTypeId(StrUtil.trimToEmpty(videoTypeId));
        videoDetails.setVideoTypeName(StrUtil.trimToEmpty(videoTypeName));
        //添加到resultItems
        page.putField("videoDetails", videoDetails);
    }

    //爬虫配置
    private Site site = Site.me()
            .setDomain("bilibili.com")//设置域名，需设置域名后，addCookie才可生效
            .setUserAgent(Constants.USER_AGENT)  //添加浏览器标识  可一定程度上减少触发反爬机制
            .setCharset("utf-8") //按照哪种字符集进行读取
            .setSleepTime(1000) //url之间的爬取间隔时间
            .setTimeOut(10000)//超时时间 毫秒
            .setRetrySleepTime(3000)//重试间隔时间 毫秒
            .setCycleRetryTimes(3) //循环重试次数  防止网络原因导致的下载失败
            .setRetryTimes(3);//重试次数

    @Override
    public Site getSite() {
        return site;
    }

}
