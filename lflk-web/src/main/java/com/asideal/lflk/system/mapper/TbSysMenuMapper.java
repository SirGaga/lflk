package com.asideal.lflk.system.mapper;

import com.asideal.lflk.system.entity.TbSysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表  Mapper 接口
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
public interface TbSysMenuMapper extends BaseMapper<TbSysMenu> {
    /**
     * 通过用户登录获取到的权限信息获取组件生成前端侧边栏
     * @param roleNames 用户被授予的角色
     * @return 返回菜单中的组件集合
     */
    List<TbSysMenu> getComponentByRoleNames(@Param("roleNames") List<String> roleNames);
}
