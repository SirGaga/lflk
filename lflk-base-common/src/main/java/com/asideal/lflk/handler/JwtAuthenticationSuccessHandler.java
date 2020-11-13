package com.asideal.lflk.handler;

import com.asideal.lflk.entity.SelfUserDetails;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.utils.token.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录成功返回前端处理
 * @author ZhangJie
 * @since 2020-11-14
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",userDetails.getId());

        String jwtToken = JwtTokenUtils.createToken(userDetails.getUsername(),claims);
        httpServletResponse.getWriter().write(Result.ok().data("token",jwtToken).toString());
    }
}
