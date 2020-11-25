package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.asideal.lflk.system.mapper.TbSysRoleMenuMapper;
import com.asideal.lflk.system.service.TbSysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统权限菜单表 服务实现类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
@Service
public class TbSysRoleMenuServiceImpl extends ServiceImpl<TbSysRoleMenuMapper, TbSysRoleMenu> implements TbSysRoleMenuService {

    /**
     * 根据角色id集合查询
     * @param roleIds 角色id集合
     * @return 角色菜单关联关系
     */
    @Override
    public List<TbSysRoleMenu> selectByRoleIds(List<Integer> roleIds) {
        return this.baseMapper.selectByRoleIds(roleIds);
    }

    /**
     * 根据角色名称（英文）进行查询
     * @param roleNames 角色英文集合
     * @return 角色菜单关联关系
     */
    @Override
    public List<TbSysRoleMenu> selectByRoleNames(List<String> roleNames) {
        return this.baseMapper.selectByRoleNames(roleNames);
    }
}
