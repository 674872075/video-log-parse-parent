package com.zh.common.util;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @program: ssm
 * @description:
 * @author: java5678
 **/
public class HttpUtil {

    //创建连接池管理对象
    static PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();


    //RequestConfig.custom()
    static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(10000)  //设置请求读取数据的超时时间
            .setConnectTimeout(30000)  ///网络请求的超时时间
            .setConnectionRequestTimeout(10000)  //连接池去获取连接的超时时间
            .build();

    static {
        // 配置最大的连接数
        manager.setMaxTotal(300);
        // 每个路由最大连接数，路由是根据host来管理的，所以这里的数量不太容易把握；
        manager.setDefaultMaxPerRoute(20);
    }

    /**
     * 获取连接对象  从连接池里面去获取
     * @return
     */
    public  static CloseableHttpClient getHttpConnection(){
        CloseableHttpClient build = HttpClients.custom().
                setConnectionManager(manager).
                setDefaultRequestConfig(requestConfig).
                build();
        return build;
    }










}
