package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表  服务类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public interface TbSysUserService extends IService<TbSysUser>, UserDetailsService {

}
