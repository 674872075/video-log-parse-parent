package com.zh.spider.videodetail.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/25 14:47
 * @Description
 */
@Slf4j
public final class SpiderPropertiesUtils {

    private static final Properties config;

    private SpiderPropertiesUtils() {
    }

    static {
        config = new Properties();
        try {
            //加载配置文件
            config.load(SpiderPropertiesUtils.class.getClassLoader().getResourceAsStream("spider-config.properties"));
        } catch (IOException e) {
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
