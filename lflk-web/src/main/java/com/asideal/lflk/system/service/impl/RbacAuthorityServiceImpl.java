package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.service.RbacAuthorityService;
import com.asideal.lflk.system.vo.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("rbacPermission")
public class RbacAuthorityServiceImpl implements RbacAuthorityService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof JwtUser) {
            // 读取用户所拥有的权限菜单
            List<TbSysMenu> tbSysMenuList = ((JwtUser) principal).getTbSysMenuList();
            for (TbSysMenu menu : tbSysMenuList) {
                if (antPathMatcher.match(menu.getUrl(), request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
