package com.zh.spider.videodetail.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/25 14:47
 * @Description
 * properties文件读取工具类
 */
@Slf4j
public final class SpiderPropertiesUtils {

    private static final Props config;

    private SpiderPropertiesUtils() {
    }

    static {
        config = new Props();
        try {
            //加载配置文件
            config.load(ResourceUtil.getResourceObj("classpath:spider-config.properties"));
        } catch (Exception e) {
            log.error("配置文件加载出错:[{}]", e.getMessage(), e);
        }
    }

    /**
     * 获取配置文件属性值
     * @param key
     * @return 返回对应的value值，如果key不存在会返回null
     */
    public static String getProperty(String key) {
        return config.getProperty(key);
    }

    /**
     * 设置key-value属性值
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
    }

}
