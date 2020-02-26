package com.zh.common.dto;

import lombok.ToString;

/**
 * @Description
 * 10000-- 通用错误代码
 */
@ToString
public enum  CommonCode implements ResultCode {
    
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    INVALID_PARAM(false,10003,"无效参数"),
    SQL_ERROR(false,10004,"Mysql语法错误，请检查sql语句");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success,int code, String message){
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
