package com.zh.analysis.pojo;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/5 13:57
 * @Description 视频信息详情
 */
@Getter
@Setter
public class VideoDetails implements WritableComparable<VideoDetails> {

    /**
     * 视频ID
     */
    private String videoAid;

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
     * 弹幕数
     */
    private String danmakuCount;

    /**
     * 评论数
     */
    private String commentCount;

    /**
     * 作者
     */
    private String videoAuthor;
    /**
     * 作者ID
     */
    private String videoAuthorMid;

    /**
     * 点赞数
     */
    private String videoLikeCount;

    /**
     * 投硬币数
     */
    private String videoCoinCount;

    /**
     * 收藏人数
     */
    private String videoCollectCount;

    /**
     * 转发人数
     */
    private String videoShareCount;

    /**
     * 综合得分
     */
    private String rankScore;

    /**
     * 发布日期，具体时间
     * 例如:2020-02-12 22:00:15
     */
    private String publishDate;

    /**
     * 频道分类  0--全部分类  1--动画 168--国产 3--音乐  129--舞蹈  4--游戏
     * 36--科技 188--数码  160--生活  119--鬼畜  155--时尚  5--娱乐 181--影视
     */
    private String videoTypeId;

    /**
     * 视频分类名字
     */
    private String videoTypeName;

    @Override
    public String toString() {
        return
                videoAid + '\001' +
                        videoName + '\001' +
                        videoUrl + '\001' +
                        playCount + '\001' +
                        danmakuCount + '\001' +
                        commentCount + '\001' +
                        videoAuthor + '\001' +
                        videoAuthorMid + '\001' +
                        videoLikeCount + '\001' +
                        videoCoinCount + '\001' +
                        videoCollectCount + '\001' +
                        videoShareCount + '\001' +
                        rankScore + '\001' +
                        publishDate + '\001' +
                        videoTypeId + '\001' +
                        videoTypeName + '\001'
                ;
    }

    @Override
    public int compareTo(VideoDetails o) {
        int i = videoAid.compareTo(o.getVideoAid());
        if (i != 0) {
            return i;
        }
        return videoTypeId.compareTo(o.getVideoTypeId());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(videoAid);
        out.writeUTF(videoName);
        out.writeUTF(videoUrl);
        out.writeUTF(playCount);
        out.writeUTF(danmakuCount);
        out.writeUTF(commentCount);
        out.writeUTF(videoAuthor);
        out.writeUTF(videoAuthorMid);
        out.writeUTF(videoLikeCount);
        out.writeUTF(videoCoinCount);
        out.writeUTF(videoCollectCount);
        out.writeUTF(videoShareCount);
        out.writeUTF(rankScore);
        out.writeUTF(videoTypeId);
        out.writeUTF(publishDate);
        out.writeUTF(videoTypeName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.videoAid = in.readUTF();
        this.videoName = in.readUTF();
        this.videoUrl = in.readUTF();
        this.playCount = in.readUTF();
        this.danmakuCount = in.readUTF();
        this.commentCount = in.readUTF();
        this.videoAuthor = in.readUTF();
        this.videoAuthorMid = in.readUTF();
        this.videoLikeCount = in.readUTF();
        this.videoCoinCount = in.readUTF();
        this.videoCollectCount = in.readUTF();
        this.videoShareCount = in.readUTF();
        this.rankScore = in.readUTF();
        this.videoTypeId = in.readUTF();
        this.publishDate = in.readUTF();
        this.videoTypeName = in.readUTF();
    }
}
