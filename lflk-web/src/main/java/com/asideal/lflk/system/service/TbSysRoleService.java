package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbSysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表  服务类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public interface TbSysRoleService extends IService<TbSysRole> {

    public IPage<TbSysRole> findRoleMenuByPage(Page<TbSysRole> page, QueryWrapper<TbSysRole> queryWrapper);

}
