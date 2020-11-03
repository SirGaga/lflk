package com.asideal.lflk.handler;

import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author ZhangJie
 * @since 2020-11-03
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理，没有指定异常类型，使用所有异常捕获
     * @param e 运行时异常
     * @return 返回错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
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
        e.printStackTrace();
        return Result.error()
                .message(ResultCode.ARITHMETIC_EXCEPTION.getMessage())
                .code(ResultCode.ARITHMETIC_EXCEPTION.getCode());
    }

    /**
     * 算数异常的捕获，其他异常可以按照此例进行编写
     * @param e 运行时算数异常
     * @return 返回具体错误码
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e) {
        e.printStackTrace();
        return Result.error()
                .message(e.getErrMsg())
                .code(e.getCode());
    }

}
