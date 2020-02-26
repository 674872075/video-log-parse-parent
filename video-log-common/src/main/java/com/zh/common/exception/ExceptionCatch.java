package com.zh.common.exception;

import com.google.common.collect.ImmutableMap;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.zh.common.dto.CommonCode;
import com.zh.common.dto.ResponseResult;
import com.zh.common.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 *配置全局异常处理器
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
    private  static ImmutableMap<Class<?  extends  Throwable>, ResultCode> EXCEPTIONS;
    //使用builder来构建一个异常类型和错误代码的异常
    protected  static  ImmutableMap.Builder<Class<?  extends  Throwable>,ResultCode>  builder  =
            ImmutableMap.builder();

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        //sql语法错误
        builder.put(MySQLSyntaxErrorException.class, CommonCode.SQL_ERROR);
    }

    //捕获CustomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        log.error("catch exception : {}\r\n exception: ", e.getMessage(), e);
        ResultCode resultCode = e.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }

    //捕获Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
         //记录日志
        log.error("catch  exception  :  {}\r\nexception:  ", e.getMessage(), e);
        if (EXCEPTIONS  ==  null)
            EXCEPTIONS  =  builder.build();
        //从builder中寻找事前存入map中的异常类所对应的resultCode
        final ResultCode resultCode  =  EXCEPTIONS.get(e.getClass());
        final ResponseResult responseResult;
        //如果不为空将resultCode取出放入responseResult返回给前台
        if  (resultCode  !=  null)  {
            responseResult  =  new  ResponseResult(resultCode);
        }  else  {
            //没找到对应的resultCode证明事先没有定义过，直接给前台返回SERVER_ERROR
            responseResult  =  new  ResponseResult(CommonCode.SERVER_ERROR);
        }
        return  responseResult;

    }

}
