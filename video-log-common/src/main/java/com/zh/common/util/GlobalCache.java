package com.zh.common.util;


import java.util.HashMap;
import java.util.Map;

public class GlobalCache {


    public static Map<String,Object> tableNameBeanMap = new HashMap<>(1000);


    public static Map<String,Object> serviceNameMap = new HashMap<>(1000);


    /**
     * 全局请求头
     */
    public static Map<String,String> header=new HashMap<>();


    /**
     * 依赖参数容器
     */
    public static Map<String,String> paramMap = new HashMap<>(1000);


}
