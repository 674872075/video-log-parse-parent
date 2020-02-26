package com.zh.analysis.pojo;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/2/20 11:59
 * @Description
 */
public class Constants {

    private Constants(){}

    public static final int FIELS_LENGTH = 16;

    public static final String URL_REGEX = "^(http[s]*)://www.bilibili.com/video/av[\\d]{8,}[\\S]*";

    public static final String DATETIME_REGEX = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
}
