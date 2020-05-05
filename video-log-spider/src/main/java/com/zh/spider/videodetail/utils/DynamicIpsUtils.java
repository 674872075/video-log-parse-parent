package com.zh.spider.videodetail.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * ========================start=========提取IP/生成IP相关说明==============start==========================
 * 1 获取代理IP
 * 建议每隔10秒一次调用，以及时获取更新的代理IP。
 * <p>
 * 1.1 接口说明
 * 调用说明
 * 属性	内容
 * URL	http://www.zdopen.com/PrivateProxy/GetIP/
 * 方法	GET
 * 编码	utf-8
 * 参数说明	api：产品的API_ID；
 * akey：产品的akey，由产品密码通过16位MD5加密得出；
 * count(可选)：提取数量, 不能超过该产品的"单提限量"，默认值等于"单提限量"；
 * adr(可选)：ip的地理位置筛选关键词，多关键词以半角逗号隔开，字符串使用UrlEncode编码；
 * order：排序方式，1表示"按上线时间从近到远"，2表示"随机抽取"；
 * fitter：1表示"过滤今日已提取过的出口IP"，2表示不过滤；
 * type：返回类型，1表示"Text文本"，2表示"XML"，3表示"JSON"。
 * 响应	返回内容的标签或字段类型可在产品管理里通过设置提取格式来定制；
 * 类型为Text文本的定制标签如下：
 * {ip}：ip地址；
 * {port}：端口；
 * {adr}：地理位置；
 * {outip}：出口ip；
 * {cometime}：上线时间(秒)。
 * 类型为XML或JSON格式的响应字段如下：
 * code：错误编号；
 * msg：描述信息；
 * data：数据体；
 * count：代理IP数量；
 * proxy_list：代理IP列表；
 * ip：ip地址；
 * port：端口；
 * adr：地理位置；
 * outip：出口ip；
 * cometime：上线时间(秒)。
 * 错误编号	10001：获取成功；
 * 12001：akey错误；
 * 12002：调用频率过快，请至少1秒调用一次；
 * 12003：参数不完整或有错误；
 * 12004：该产品不存在或已过期；
 * 12005：该产品已过期；
 * 12006：该产品已被冻结或禁用；
 * 12007：该产品当前授权模式为"终端IP授权"，但尚未绑定终端IP；
 * 12008：该产品使用额度已经超限；
 * 12009：该参数条件下当前没有任何代理IP。
 * 1.2 接口示例
 * 请求：
 * GET /PrivateProxy/GetIP/?api=1234567890&akey=8a17ca305f683620&count=5&fitter=2&order=2&type=3 HTTP/1.1
 * Host: www.zdopen.com
 * 响应：
 * HTTP/1.1 200 OK
 * Content-Type: application/json; charset=utf-8
 * Date: Thu, 25 Apr 2019 10:48:42 GMT
 * Content-Length: 405
 * <p>
 * {
 * "code": "10001",
 * "msg": "获取成功",
 * "data": {
 * "count": 5,
 * "proxy_list": [
 * {
 * "ip": "221.229.166.87",
 * "port": 11756,
 * "adr": "浙江省湖州市 电信",
 * "outip": "218.75.53.122",
 * "cometime": 49591
 * },
 * {
 * "ip": "58.218.213.47",
 * "port": 11214,
 * "adr": "江苏省镇江市 联通",
 * "outip": "122.194.25.181",
 * "cometime": 1499
 * },
 * {
 * "ip": "221.229.162.69",
 * "port": 10146,
 * "adr": "北京市 联通数据中心",
 * "outip": "123.126.85.164",
 * "cometime": 57223
 * },
 * {
 * "ip": "58.218.213.112",
 * "port": 11092,
 * "adr": "江苏省南通市启东市 电信",
 * "outip": "121.232.9.187",
 * "cometime": 1201
 * },
 * {
 * "ip": "58.218.213.44",
 * "port": 10674,
 * "adr": "广东省潮州市 电信",
 * "outip": "113.107.217.13",
 * "cometime": 23105
 * }
 * ]
 * }
 * }
 * ========================end=========提取IP/生成IP相关说明==============end==========================
 *
 * @author zhouhao
 * @version 1.0
 * @date 2020/5/4 9:51
 * @Description 动态获取代理Ip工具类   接口调用: 站大爷 https://www.zdaye.com/
 */
@Slf4j
public final class DynamicIpsUtils {
    /**
     * 获取代理IP列表接口地址
     */
    private static final String DYNAMIC_IPS_URL = SpiderPropertiesUtils.getProperty("dynamic.ips.url");

    /**
     * 测试代理IP是否可用接口地址
     */
    private static final String TEST_ADDR = "https://www.baidu.com/";

    /**
     * 代理服务器用户名
     */
    private static final String USER_NAME = SpiderPropertiesUtils.getProperty("dynamic.ips.username");

    /**
     * 代理服务器密码
     */
    private static final String PASSWORD = SpiderPropertiesUtils.getProperty("dynamic.ips.password");

    private DynamicIpsUtils() {
    }

    /**
     * 动态获取Ip列表，会检测IP可用性，不可用的IP不会放入List，
     * List永远不会为null，但size可能为0
     *
     * @return 返回Proxy列表
     * @see SimpleHttpClient
     * WebMagic封装的HttpClient简单实现，使用更加简单
     */
    public static List<Proxy> getIps() {
        //Proxy列表 封装代理服务器IP和端口
        List<Proxy> proxyList = Lists.newArrayList();
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectTimeout(10000) //设置连接超时时间
                .setSocketTimeout(10000)  //设置请求读取数据的超时时间
                .setConnectionRequestTimeout(3000); //设置从connectManager获取Connection超时时间


        // 创建Get请求
        HttpGet httpGet = new HttpGet(DYNAMIC_IPS_URL);
        HttpGet testHttpGet = new HttpGet(TEST_ADDR);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (Objects.isNull(responseEntity) || response.getStatusLine().getStatusCode() != 200) {
                return proxyList;
            }
            JSONObject responseEntityMap = JSON.parseObject(EntityUtils.toString(responseEntity));
            if (!responseEntityMap.getString("code").equals("10001")) {
                return proxyList;
            }
            JSONObject datas = JSON.parseObject(responseEntityMap.getString("data"));
            List<JSONObject> proxy_list = JSON.parseArray(datas.getString("proxy_list"), JSONObject.class);

            for (JSONObject proxy_ip : proxy_list) {
                String ip = proxy_ip.getString("ip");
                Integer port = proxy_ip.getInteger("port");

                CloseableHttpResponse testResponse = null;
                try {
                    HttpHost httpHostProxy = new HttpHost(ip, port);
                    RequestConfig requestConfig = builder.setProxy(httpHostProxy).build();
                    testHttpGet.setConfig(requestConfig);
                    testResponse = httpClient.execute(testHttpGet);
                } catch (Exception e) {
                    continue;
                } finally {
                    if (testResponse != null) {
                        //确保连接能被复用
                        EntityUtils.consumeQuietly(testResponse.getEntity());
                        testResponse.close();
                    }
                }
                Proxy proxy = new Proxy(ip, port, USER_NAME, PASSWORD);
                proxyList.add(proxy);
            }
        } catch (ClientProtocolException e) {
            log.error("获取动态IP时出错，错误信息: [{}]", e.getMessage(), e);
        } catch (ParseException e) {
            log.error("获取动态IP时出错，错误信息: [{}]", e.getMessage(), e);
        } catch (IOException e) {
            log.error("获取动态IP时出错，错误信息: [{}]", e.getMessage(), e);
        } catch (Exception e) {
            log.error("获取动态IP时出错，错误信息: [{}]", e.getMessage(), e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("获取动态IP时出错，错误信息: [{}]", e.getMessage(), e);
            }
        }
        return proxyList;
    }

}
