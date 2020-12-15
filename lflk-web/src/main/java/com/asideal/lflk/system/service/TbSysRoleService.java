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

    /**
     * 分页查询用户菜单信息
     * @param page 分页信息
     * @param queryWrapper 查询条件
     * @return
     */
    IPage<TbSysRole> findRoleMenuByPage(Page<TbSysRole> page, QueryWrapper<TbSysRole> queryWrapper);

}
