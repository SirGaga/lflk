package com.asideal.lflk.base.controller;

import cn.hutool.core.date.DateUtil;
import com.asideal.lflk.base.entity.BaseEntity;
import com.asideal.lflk.security.service.AuthenticationService;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysUserService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sun.istack.internal.NotNull;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * controller的基类
 *
 * 保存一些常用方法
 *
 * @author family
 */
public class BaseController {

    /**
     * menu类型
     * 目录
     */
    public static final String MENU_TYPE_DIR = "0";

    /**
     * menu类型
     * 菜单
     */
    public static final String MENU_TYPE_MENU = "1";

    /**
     * menu类型
     * 按钮
     */
    public static final String MENU_TYPE_BUTTON = "2";

    /**
     * menu类型
     * 目录
     * 组件字段默认值
     */
    public static final String MENU_TYPE_DIR_COMPONENT = "Layout";

    public static final String MENU_META_AFFIX = "1";

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

    public <T extends BaseEntity> void prepareSaveInfo(@NotNull T entity){
        TbSysUser operateUser = getUserByAuthentication(getAuthentication());
        entity.setCreateUserId(operateUser.getId());
        entity.setCreateUserName(operateUser.getUserName());
        entity.setCreateTime(DateUtil.date(Calendar.getInstance()));
        entity.setUpdateUserId(operateUser.getId());
        entity.setUpdateUserName(operateUser.getUserName());
        entity.setUpdateTime(DateUtil.date(Calendar.getInstance()));
    }

    public <T extends BaseEntity> void prepareUpdateInfo(@NotNull T entity){
        TbSysUser operateUser = getUserByAuthentication(getAuthentication());
        entity.setUpdateTime(DateUtil.date(Calendar.getInstance()));
        entity.setUpdateUserId(operateUser.getId());
        entity.setUpdateUserName(operateUser.getUserName());
    }

}
