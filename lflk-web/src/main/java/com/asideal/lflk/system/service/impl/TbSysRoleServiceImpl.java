package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.entity.TbSysRole;
import com.asideal.lflk.system.mapper.TbSysRoleMapper;
import com.asideal.lflk.system.service.TbSysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表  服务实现类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public class TbSysRoleServiceImpl extends ServiceImpl<TbSysRoleMapper, TbSysRole> implements TbSysRoleService {

    @Override
    public IPage<TbSysRole> findRoleMenuByPage(Page<TbSysRole> page, QueryWrapper<TbSysRole> queryWrapper) {
        return this.baseMapper.findRoleMenuByPage(page, queryWrapper);
    }

}
