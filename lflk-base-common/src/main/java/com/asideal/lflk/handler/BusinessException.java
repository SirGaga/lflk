package com.asideal.lflk.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常处理器
 * 主要处理业务场景下发生的非运行时异常
 * 比如：找不到用户，用户未登录等等
 * @author ZhangJie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    @ApiModelProperty(value = "错误状态码")
    private Integer code;
    @ApiModelProperty(value = "错误信息")
    private String errMsg;



}
