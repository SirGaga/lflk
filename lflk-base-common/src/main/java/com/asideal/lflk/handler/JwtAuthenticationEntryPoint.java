package com.asideal.lflk.handler;

import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.utils.response.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录的处理返回
 * @author ZhangJie
 * @since 2020-11-13
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseUtils.responseResult(response, Result.error().code(ResultCode.USER_NOT_LOGIN.getCode()).message(ResultCode.USER_NOT_LOGIN.getMessage()));
    }
}
