package com.zh.spider.videodetail.proxyprovider;

import cn.hutool.core.collection.CollUtil;
import com.zh.spider.videodetail.utils.DynamicIpsUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 动态IP池提供者,IP轮询完之后再次更换IP池,IP获取数量不能设置太大，适合IP存在时间较短的场景
 */
public class DynamicProxyProvider implements ProxyProvider {

    private volatile List<Proxy> proxies;

    private final AtomicInteger pointer;

    private final int START_INDEX = 0;

    public DynamicProxyProvider(List<Proxy> proxies) {
        this(proxies, new AtomicInteger(-1));
    }

    private DynamicProxyProvider(List<Proxy> proxies, AtomicInteger pointer) {
        this.proxies = proxies;
        this.pointer = pointer;
    }

    public static DynamicProxyProvider from(Proxy... proxies) {
        List<Proxy> proxiesTemp = new ArrayList<Proxy>(proxies.length);
        for (Proxy proxy : proxies) {
            proxiesTemp.add(proxy);
        }
        return new DynamicProxyProvider(Collections.unmodifiableList(proxiesTemp));
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
        //Donothing
    }

    @Override
    public Proxy getProxy(Task task) {
        return proxies.get(incrForLoop());
    }

    private int incrForLoop() {
        int p = pointer.incrementAndGet();
        int size = proxies.size();
        if (p < size) {
            return p;
        }
        List<Proxy> ips = DynamicIpsUtils.getIps();
        if (CollUtil.isNotEmpty(ips)) {
            proxies = ips;
            while (!pointer.compareAndSet(p, START_INDEX)) {
                p = pointer.get();
            }
            return START_INDEX;
        }
        while (!pointer.compareAndSet(p, p % size)) {
            p = pointer.get();
        }
        return p % size;
    }
}
