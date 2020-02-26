package com.zh.common.exception;


import com.zh.common.dto.ResultCode;

/**
 * @description
 * 用于抛出自定义异常
 */
public class ExceptionCast {

    /*禁止其他类直接创建本类对象*/
    private  ExceptionCast(){}

    //使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
