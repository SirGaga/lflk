package com.asideal.lflk.system.mapper;

import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统权限菜单表 Mapper 接口
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
public interface TbSysRoleMenuMapper extends BaseMapper<TbSysRoleMenu> {
    List<TbSysRoleMenu> selectByRoleIds(@Param("roleIds") List<Integer> roleIds);

    List<TbSysRoleMenu> selectByRoleNames(@Param("roleNames") List<String> selectByRoleNames);
}
