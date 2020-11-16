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

    @Override
    public List<TbSysRoleMenu> selectByRoleIds(List<Integer> roleIds) {
        return this.baseMapper.selectByRoleIds(roleIds);
    }
}
