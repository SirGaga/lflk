package com.asideal.lflk.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.asideal.lflk.system.service.RbacAuthorityService;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.service.TbSysRoleMenuService;
import com.asideal.lflk.system.vo.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component("rbacPermission")
public class RbacAuthorityServiceImpl implements RbacAuthorityService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private TbSysRoleMenuService tbSysRoleMenuService;

    @Resource
    private TbSysMenuService tbSysMenuService;
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof JwtUser) {
            // 读取用户所拥有的权限菜单
            JwtUser jwtUser = (JwtUser) principal;
            List<String> roleNames = jwtUser.getAuthorities().stream().collect(Collectors.toList()).stream().map(e -> e.getAuthority()).collect(Collectors.toList());
            // 获取权限范围内的菜单
            List<TbSysRoleMenu> tbSysRoleMenus = tbSysRoleMenuService.selectByRoleNames(roleNames);
            List<TbSysMenu> tbSysMenus = tbSysMenuService.getBaseMapper().selectBatchIds(
                    tbSysRoleMenus.stream().map(e -> e.getMenuId()).collect(Collectors.toList())
            ).stream().filter(e -> StrUtil.isNotEmpty(e.getPath())).collect(Collectors.toList());

            // 将菜单url和requestURI进行比较
            for (TbSysMenu menu : tbSysMenus) {
                if (antPathMatcher.match(menu.getPath(), request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
