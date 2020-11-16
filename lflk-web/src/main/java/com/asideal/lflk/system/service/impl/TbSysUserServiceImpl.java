package com.asideal.lflk.system.service.impl;

import com.asideal.lflk.system.entity.*;
import com.asideal.lflk.system.mapper.TbSysUserMapper;
import com.asideal.lflk.system.service.*;
import com.asideal.lflk.system.vo.JwtUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TbSysUserRoleService tbSysUserRoleService;

    @Resource
    private TbSysRoleService tbSysRoleService;

    @Resource
    private TbSysRoleMenuService tbSysRoleMenuService;

    @Resource
    private TbSysMenuService tbSysMenuService;

    /**
     * spring security 重写加载用户指定的信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TbSysUser user = this.baseMapper.getUserByUserName(username);
        List<TbSysUserRole> sysUserRoles = tbSysUserRoleService.getTbSysUserRoleByUserId(user.getId());
        // 根据角色id批量查询
        List<TbSysRole> tbSysRoles = tbSysRoleService.getBaseMapper()
                .selectBatchIds(
                        sysUserRoles.stream().map(e -> e.getId()).collect(Collectors.toList())
                );

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        tbSysRoles.forEach(e -> authorities.add(new SimpleGrantedAuthority(e.getRoleName())));

        List<TbSysRoleMenu> tbSysRoleMenus = tbSysRoleMenuService.selectByRoleIds(sysUserRoles.stream().map(e -> e.getRoleId()).collect(Collectors.toList()));
        List<TbSysMenu> tbSysMenus = tbSysMenuService.getBaseMapper().selectBatchIds(
                tbSysRoleMenus.stream().map(e -> e.getId()).collect(Collectors.toList())
        );


        return new JwtUser(user,authorities,tbSysMenus);
    }
}
