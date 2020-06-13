package com.zh.common.util;

import org.springframework.util.Assert;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * IO流拓展工具类，补充IOUtils新版本中废弃的closeQuietly
 * @author King
 * @since 2018/12/27 17:56
 */
public class ExtendedIOUtils {

    public static void flush(Flushable... resources) throws IOException {
        Assert.noNullElements(resources, "resources invalid");
        int length = resources.length;
        for(int i = 0; i < length; ++i) {
            Flushable resource = resources[i];
            if (resource != null) {
                resource.flush();
            }
        }
    }

    public static void closeQuietly(Closeable... resources) {
        int length = resources.length;
        for(int i = 0; i < length; ++i) {
            Closeable resource = resources[i];
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    //ignore exception
                }
            }
        }
    }

}
