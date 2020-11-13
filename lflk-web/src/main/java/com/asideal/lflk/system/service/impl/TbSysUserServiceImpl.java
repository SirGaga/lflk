package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.vo.JwtUser;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.mapper.TbSysUserMapper;
import com.asideal.lflk.system.service.TbSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表  服务实现类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public class TbSysUserServiceImpl extends ServiceImpl<TbSysUserMapper, TbSysUser> implements TbSysUserService {
    @Resource
    TbSysUserMapper tbSysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TbSysUser user = tbSysUserMapper.getUserAndRoleByUserName(username);
        return new JwtUser(user);
    }
}
