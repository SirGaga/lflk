package com.asideal.lflk.system.mapper;

import com.asideal.lflk.system.entity.TbSysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
public interface TbSysUserRoleMapper extends BaseMapper<TbSysUserRole> {

    /**
     * 根据用户id获取角色id
     * @param userId 用户id
     * @return 角色ID
     */
    List<TbSysUserRole> getTbSysUserRoleByUserId(Integer userId);

}
