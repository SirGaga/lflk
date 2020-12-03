package com.asideal.lflk.base.controller;

import com.asideal.lflk.security.service.AuthenticationService;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysUserService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;

/**
 * controller的基类
 *
 * 保存一些常用方法
 *
 * @author family
 */
public class BaseController {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private TbSysUserService tbSysUserService;

    public Authentication getAuthentication() {
        return authenticationService.getAuthentication();
    }

    public TbSysUser getUserByAuthentication(Authentication authentication) {
        return tbSysUserService.getOne(new LambdaUpdateWrapper<TbSysUser>().eq(TbSysUser::getUserName, authentication.getName()));
    }

}
