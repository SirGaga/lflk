package com.asideal.lflk.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限访问控制
 * 这里通过获取到的用户中带着的权限信息/角色信息（这里取决于RBAC到底是那种模型）来获取菜单信息
 *
 * @author ZhangJie
 * @since 2020-11-13
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object userInfo = authentication.getPrincipal();

        boolean hasPermission  = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();
            //获取资源
            Set<String> urls = new HashSet();
            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            urls.add("/demo/**");

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }
}
