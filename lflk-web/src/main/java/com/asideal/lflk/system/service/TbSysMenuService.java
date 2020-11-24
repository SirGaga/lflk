package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbSysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表  服务类
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Service
public interface TbSysMenuService extends IService<TbSysMenu> {
    /**
     * 通过用户登录获取到的权限信息获取组件生成前端侧边栏
     * @param roleNames 用户被授予的角色
     * @return 返回菜单中的组件集合
     */
    List<TbSysMenu> getComponentByRoleNames(List<String> roleNames);

}
