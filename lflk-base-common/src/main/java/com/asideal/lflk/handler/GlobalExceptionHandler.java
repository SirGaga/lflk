package com.asideal.lflk.handler;

import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author ZhangJie
 * @since 2020-11-03
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * 全局异常处理，没有指定异常类型，使用所有异常捕获
     * @param e 运行时异常
     * @return 返回错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error(e.getMessage());
        return Result.error();
    }

    /**
     * 算数异常的捕获，其他异常可以按照此例进行编写
     * @param e 运行时算数异常
     * @return 返回具体错误码
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        log.error(e.getMessage());
        return Result.error()
                .message(ResultCode.ARITHMETIC_EXCEPTION.getMessage())
                .code(ResultCode.ARITHMETIC_EXCEPTION.getCode());
    }

    /**
     * 参数异常的捕获
     * @param e 运行时参数异常
     * @return 返回具体错误码
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result error(IllegalArgumentException e) {
        log.error(e.getMessage());
        return Result.error()
                .message(ResultCode.PARAM_NOT_VALID.getMessage())
                .code(ResultCode.PARAM_NOT_VALID.getCode());
    }

    /**
     * 用户名密码错误
     * @param e 运行时参数异常
     * @return 返回具体错误码
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public Result error(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return Result.error()
                .message(ResultCode.USER_CREDENTIALS_ERROR.getMessage())
                .code(ResultCode.USER_CREDENTIALS_ERROR.getCode());
    }

    /**
     * 算数异常的捕获，其他异常可以按照此例进行编写
     * @param e 运行时算数异常
     * @return 返回具体错误码
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e) {
        log.error(e.getErrMsg());
        return Result.error()
                .message(e.getErrMsg())
                .code(e.getCode());
    }

}
