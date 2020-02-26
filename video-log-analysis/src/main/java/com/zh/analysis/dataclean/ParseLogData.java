package com.zh.analysis.dataclean;

import com.zh.analysis.pojo.Constants;
import com.zh.analysis.pojo.Fileds;

import java.util.regex.Pattern;

public class ParseLogData {
    public static boolean parseLog(String line) {

        //1.按照"\001"切割数据 会转化为\u0001 一个字符
        String[] split = line.split("\001");
       // Splitter.on('\001').omitEmptyStrings().splitToList(line);

        long length = split.length;
        //2.判断数组长度 16
        if (split.length != Constants.FIELS_LENGTH) {
            //非法数据返回
            return false;
        }

        //日期格式不符合会被清洗
        String pubdate = split[Fileds.PUBLISH_DATE];
        if (!Pattern.compile(Constants.DATETIME_REGEX).matcher(pubdate).matches()) {
            return false;
        }

        //判断检查host字段为标准格式
        String host = split[Fileds.VIDEO_URL];

        return Pattern.compile(Constants.URL_REGEX).matcher(host).matches();
    }
}
