package com.zh.spider.rankinfo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 13:57
 * @Description 视频信息
 */
@Data
public class VideoInfo implements Serializable {

    /**
     * 视频名字
     */
    private String videoName;

    /**
     * 视频url
     */
    private String videoUrl;

    /**
     * 播放量
     */
    private String playCount;

    /**
     * 评论数
     */
    private String commentCount;

    /**
     * 作者
     */
    private String videoAuthor;

    /**
     * 综合得分
     */
    private String compositeScore;

    /**
     * 频道分类  0--全部分类  1--动画 168--国产 3--音乐  129--舞蹈  4--游戏
     * 36--科技 188--数码  160--生活  119--鬼畜  155--时尚  5--娱乐 181--影视
     */
    private String videoTypeId;

    /**
     * 发布日期
     * 0--全部投稿
     * 1--近期投稿
     */
    @Deprecated
    private String publishDate;

    /**
     * 排行榜类型
     * 1--日排行
     * 3--三日排行
     * 7--周排行
     * 30--月排行
     */
    @Deprecated
    private String rankingType;

    @Override
    public String toString() {
        return
                videoName + '\001' +
                        videoUrl + '\001' +
                        playCount + '\001' +
                        commentCount + '\001' +
                        videoAuthor + '\001' +
                        compositeScore + '\001'+
                        videoTypeId;
    }
}
