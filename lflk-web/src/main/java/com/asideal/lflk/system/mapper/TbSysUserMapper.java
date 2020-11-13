package com.asideal.lflk.system.mapper;

import com.asideal.lflk.system.entity.TbSysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表  Mapper 接口
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
public interface TbSysUserMapper extends BaseMapper<TbSysUser> {

    TbSysUser getUserAndRoleByUserName(String userName);

}
