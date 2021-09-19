package com.zh.common.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Httpclient 全局请求头、依赖参数设置
 * @author zhouhao
 */
public final class GlobalCache {

    private GlobalCache(){}

    public static final Map<String,Object> tableNameBeanMap = new ConcurrentHashMap<>(1000);


    public static final Map<String,Object> serviceNameMap = new ConcurrentHashMap<>(1000);


    /**
     * 全局请求头
     */
    public static final Map<String,String> header=new ConcurrentHashMap<>();


    /**
     * 依赖参数容器
     */
    public static final Map<String,String> paramMap = new ConcurrentHashMap<>(1000);


    static {
        header.put("Content-Type","application/json;charset=UTF-8");//Content-Type默认支持json
    }
}
