package com.zh.common.exception;

import com.zh.common.dto.ResultCode;

/**
 * @date 2019/11/21 10:36
 * @Description
 */
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        //异常信息为错误代码+异常信息
        super("错误代码："+resultCode.code()+"   错误信息："+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }
}
