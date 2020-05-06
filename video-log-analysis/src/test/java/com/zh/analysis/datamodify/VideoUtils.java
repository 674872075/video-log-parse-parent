package com.zh.analysis.datamodify;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/5/5 22:00
 * @Description 视频工具类，
 * 目前暂时用来处理视频分类ID和视频分类名称的映射问题,
 * 避免出现一个videoTypeId对应多个videoTypeName
 */
public final class VideoUtils {

    /**
     * 视频类型映射
     */
    private static final Map<String, String> videoTypeMap;

    /**
     *  初始化videoTypeMap
     */
    static {
        videoTypeMap = Maps.newHashMap();
        videoTypeMap.put("24", "动画");
        videoTypeMap.put("51", "番剧");
        videoTypeMap.put("168", "国创");
        videoTypeMap.put("31", "音乐");
        videoTypeMap.put("199", "舞蹈");
        videoTypeMap.put("17", "游戏");
        videoTypeMap.put("124", "科技");
        videoTypeMap.put("95", "数码");
        videoTypeMap.put("138", "生活");
        videoTypeMap.put("22", "鬼畜");
        videoTypeMap.put("164", "时尚");
        videoTypeMap.put("166", "广告");
        videoTypeMap.put("71", "娱乐");
        videoTypeMap.put("182", "影视");
        videoTypeMap.put("174", "其他");
    }

    private VideoUtils() {
    }

    /**
     * 根据视频分类ID获取视频分类名称,如果videoTypeId不存在,返回null
     *
     * @param videoTypeId 视频分类ID
     * @return
     */
    public static String getVideoTypeNameByID(String videoTypeId) {
        return videoTypeMap.get(videoTypeId);
    }

}
