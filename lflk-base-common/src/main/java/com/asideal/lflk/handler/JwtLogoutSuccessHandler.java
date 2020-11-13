package com.asideal.lflk.handler;

import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成功退出的处理
 * @author ZhangJie
 * @since 2020-11-14
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.getWriter().write(Result.ok().data("result", ResultCode.USER_LOGOUT_SUCCESS.getMessage()).toString());
    }
}
