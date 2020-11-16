package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统权限菜单表 服务类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
public interface TbSysRoleMenuService extends IService<TbSysRoleMenu> {
    List<TbSysRoleMenu> selectByRoleIds(List<Integer> roleIds);
}
