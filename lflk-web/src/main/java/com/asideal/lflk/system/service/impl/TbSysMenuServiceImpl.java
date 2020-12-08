package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.mapper.TbSysMenuMapper;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表  服务实现类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public class TbSysMenuServiceImpl extends ServiceImpl<TbSysMenuMapper, TbSysMenu> implements TbSysMenuService {

    @Override
    public List<TbSysMenu> getComponentByRoleNames(List<String> roleNames) {
        return this.baseMapper.getComponentByRoleNames(roleNames);
    }

    @Override
    public List<TbSysMenu> getMenuTreeTable() {
        return this.baseMapper.getMenuTreeTable();
    }
}
