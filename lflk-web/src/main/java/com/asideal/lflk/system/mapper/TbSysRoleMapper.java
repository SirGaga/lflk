package com.asideal.lflk.system.mapper;

import com.asideal.lflk.system.entity.TbSysRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色表  Mapper 接口
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
public interface TbSysRoleMapper extends BaseMapper<TbSysRole> {

    /**
     * 分页获取角色菜单对应情况
     * @param page 分页
     * @param wrapper 查询条件
     * @return 返回分页结果
     */
    IPage<TbSysRole> findRoleMenuByPage(IPage<TbSysRole> page, @Param(Constants.WRAPPER) Wrapper<TbSysRole> wrapper);

}
