package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbSysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
public interface TbSysUserRoleService extends IService<TbSysUserRole> {
     List<TbSysUserRole> getTbSysUserRoleByUserId(Integer userId);
}
