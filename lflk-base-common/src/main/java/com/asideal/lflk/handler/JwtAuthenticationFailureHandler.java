package com.asideal.lflk.handler;

import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.utils.response.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登陆失败的处理
 * @author ZhangJie
 * @since 2020-11-14
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ResponseUtils.responseResult(response,Result.error().code(ResultCode.USER_CREDENTIALS_ERROR.getCode()).message(ResultCode.USER_CREDENTIALS_ERROR.getMessage()));
    }
}
