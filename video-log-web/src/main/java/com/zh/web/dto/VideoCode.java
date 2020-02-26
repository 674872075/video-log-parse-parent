package com.zh.web.dto;

import com.zh.common.dto.ResultCode;
import lombok.ToString;

/**
 * @Description
 * 23000-- 用户中心错误代码
 */
@ToString
public enum VideoCode implements ResultCode {
    VIDEO_QUERY_SUCCESS(true,23007,"查询视频成功！"),
    VIDEO_QUERY_FAIL(false,23007,"查询视频失败！"),
    VIDEO_QUERY_NOT_EXIST(false,23008,"视频不存在！");

    //操作结果
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private VideoCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
