package com.zh.common.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @description: http https工具类
 * @author: zhouhao
 **/
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    // 连接超时时间
    public static final int CONNECTION_TIMEOUT = 5000;

    // 请求超时时间
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    // 数据读取等待超时
    public static final int SOCKET_TIMEOUT = 10000;

    // http
    public static final String HTTP = "http";

    // https
    public static final String HTTPS = "https";

    // http端口
    public static final int DEFAULT_HTTP_PORT = 80;

    // https端口
    public static final int DEFAULT_HTTPS_PORT = 443;

    // 默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";


    /**
     * 根据请求头选择相应的client
     * https HttpUtil.createSSLInsecureClient
     * http  createDefault
     *
     * @param url (url不带参数，例：http://test.com)
     * @return CloseableHttpClient
     */

    private static CloseableHttpClient getHttpClient(String url) {
        CloseableHttpClient httpClient = null;
        try {
            if(url.startsWith(HTTPS)){
                //创建一个SSL信任所有证书的httpClient对象
                httpClient = createSSLInsecureClient();
            }else {
                httpClient = HttpClients.createDefault();
            }
        } catch (Exception e) {
            log.error("请求client 初始化失败 请检查地址是否正确,url=" + url + " error" + e);
            throw new RuntimeException(e);
        }
        return httpClient;
    }

    /**
     * 获取post请求头
     *
     * @param url (url不带参数，例：http://test.com)
     * @return HttpPost
     */
    public static HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setRedirectsEnabled(true)
                .build();
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

    /**
     * get请求(1.处理http请求;2.处理https请求,信任所有证书)
     *
     * @param url (只能是http或https请求)
     */
    public static ResponeVo get(String url) throws IOException {
        log.info("----->调用请求 url:" + url);
        String result = "";
        // 处理参数
        HttpGet httpGet;
        CloseableHttpClient httpClient = null;
        httpClient = getHttpClient(url);
        httpGet = new HttpGet(url);
        //加入请求头
        if (GlobalCache.header != null) {
            for (String key : GlobalCache.header.keySet()) {
                String value = GlobalCache.header.get(key);
                httpGet.setHeader(key, value);
            }
        }
        //加入全局请求令牌权限
        httpGet.setHeader("Http-Authorization", GlobalCache.paramMap.get("token"));
        //httpGet.setHeader(":path",url.replace("https://fe-api.zhaopin.com", ""));

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                //默认允许自动重定向
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);
        return baseRequest(httpClient, httpGet);

    }


    /**
     * post请求(1.处理http请求;2.处理https请求,信任所有证书)
     *
     * @param url
     * @param jsonParams 入参是个json字符串
     * @return
     */
    public static ResponeVo post(String url, String jsonParams) throws IOException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        Assert.hasText(url, "url invalid");
        String result;
        CloseableHttpClient httpClient;
        httpClient = getHttpClient(url);

        CloseableHttpResponse response = null;
        HttpPost httpPost = getHttpPost(url);
        if (GlobalCache.header != null) {
            for (String key : GlobalCache.header.keySet()) {
                String value = GlobalCache.header.get(key);
                httpPost.setHeader(key, value);
            }
        }
        //加入全局请求令牌权限
        httpPost.setHeader("Http-Authorization", GlobalCache.paramMap.get("token"));
        if (GlobalCache.header.get("Content-Type") != null) {
            String contentType = GlobalCache.header.get("Content-Type");
            if ("application/x-www-form-urlencoded".equals(contentType)) {
                JSONObject jsonObject = JSONObject.parseObject(jsonParams);
                List<NameValuePair> params = new ArrayList<>();
                //循环json key value 仅能解决正常对象  若Json对象中嵌套数组 则可能需要单独处理
                if (jsonObject != null) {
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(params,DEFAULT_ENCODING));
                }
            }
            if ("application/json;charset=UTF-8".equals(contentType)) {
                httpPost.setEntity(new StringEntity(jsonParams, ContentType.create("application/json", DEFAULT_ENCODING)));
            }
        }else{
          log.error("请求头为空");
        }
        return baseRequest(httpClient, httpPost);
    }


    /**
     * get请求(1.处理http请求;2.处理https请求,信任所有证书)
     *
     * @param url (只能是http或https请求)
     * @return
     */
    public static ResponeVo delete(String url) throws IOException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        httpClient = getHttpClient(url);
        HttpDelete httpDelete = new HttpDelete(url);
        if (GlobalCache.header != null) {
            for (String key : GlobalCache.header.keySet()) {
                String value = GlobalCache.header.get(key);
                httpDelete.setHeader(key, value);
            }
        }
        httpDelete.setHeader("Http-Authorization", GlobalCache.paramMap.get("token"));
        return baseRequest(httpClient, httpDelete);
    }


    /**
     * get请求(1.处理http请求;2.处理https请求,信任所有证书)
     *
     * @param url (只能是http或https请求)
     * @return
     */
    public static ResponeVo put(String url, String jsonParams) throws IOException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {
        log.info("----->调用请求 url:" + url + " ---->json参数:" + jsonParams);
        CloseableHttpClient httpClient = null;
        String content;
        httpClient = getHttpClient(url);
        CloseableHttpResponse response = null;
        HttpPut httpPut = new HttpPut(url);
        if (GlobalCache.header != null) {
            for (String key : GlobalCache.header.keySet()) {
                String value = GlobalCache.header.get(key);
                httpPut.setHeader(key, value);
            }
        }
        //加入全局请求令牌权限
        httpPut.setHeader("Http-Authorization", GlobalCache.paramMap.get("token"));
        if (GlobalCache.header.get("Content-Type") != null) {
            String contentType = GlobalCache.header.get("Content-Type");
            if ("application/x-www-form-urlencoded".equals(contentType)) {
                JSONObject jsonObject = JSONObject.parseObject(jsonParams);
                List<NameValuePair> params = new ArrayList<>();
                //循环json key value 仅能解决正常对象  若Json对象中嵌套数组 则可能需要单独处理
                if (jsonObject != null) {
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                    httpPut.setEntity(new UrlEncodedFormEntity(params,DEFAULT_ENCODING));
                }
            }
            if ("application/json;charset=UTF-8".equals(contentType)) {
                httpPut.setEntity(new StringEntity(jsonParams, ContentType.create("application/json", DEFAULT_ENCODING)));
            }
        }else{
            log.error("请求头为空");
        }
        return baseRequest(httpClient, httpPut);
    }


    /**
     * 采用绕过验证的方式处理https请求
     *
     * @param url
     * @param reqMap
     * @param encoding
     * @return
     */
    public static ResponeVo postSSLUrl(String url, Map<String, Object> reqMap, String encoding) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        String result;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponeVo responeVo = null;
        // 添加参数
        List<NameValuePair> params = buildParams(reqMap);
        try {
            //采用绕过验证的方式处理https请求
            HostnameVerifier hostnameVerifier = (hostname, session) -> true;
            SSLContext sslcontext = createIgnoreVerifySSL();
            //设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext, hostnameVerifier))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            //创建自定义的httpclient对象
            httpClient = HttpClients.custom().setConnectionManager(connManager).build();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params,DEFAULT_ENCODING));
            //指定报文头Content-type、User-Agent
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            //执行请求操作，并拿到结果（同步阻塞）
            responeVo = baseRequest(httpClient, httpPost);
        } finally {
            ExtendedIOUtils.closeQuietly(httpClient);
            ExtendedIOUtils.closeQuietly(response);
        }
        return responeVo;
    }

    private static List<NameValuePair> buildParams(Map<String, Object> reqMap) {
        List<NameValuePair> params = new ArrayList<>();
        if (reqMap != null && reqMap.keySet().size() > 0) {
            Iterator<Map.Entry<String, Object>> iter = reqMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entity = iter.next();
                params.add(new BasicNameValuePair(entity.getKey(), entity.getValue().toString()));
            }
        }
        return params;
    }

    /**
     * 创建一个SSL信任所有证书的httpClient对象
     *
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // 默认信任所有证书
        HostnameVerifier hostnameVerifier = (hostname, session) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,(chain, authType) -> true).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());
        return sc;
    }


    private static String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        // Return full string
        return total.toString();
    }


    public static ResponeVo baseRequest(CloseableHttpClient httpClient, HttpUriRequest request) {
        ResponeVo responeVo = new ResponeVo();
        CloseableHttpResponse response = null;
        try {
            String content;
            response = httpClient.execute(request);
            content = inputStreamToString(response.getEntity().getContent());
            responeVo.setCode(response.getStatusLine().getStatusCode());
            responeVo.setContent(content);
            responeVo.setResponse(response);
            log.info("http调用完成,返回数据" + content);
        } catch (Exception e) {
            log.error(" http调用失败:" + e);
        }
        ExtendedIOUtils.closeQuietly(httpClient);
        ExtendedIOUtils.closeQuietly(response);
        return responeVo;
    }

}
