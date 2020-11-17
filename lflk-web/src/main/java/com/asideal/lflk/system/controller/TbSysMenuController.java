package com.asideal.lflk.system.controller;


import com.asideal.lflk.response.Result;
import com.asideal.lflk.system.entity.TbSysDept;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.service.TbSysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/system/menu")
@Api(value = "系统菜单管理模块",tags = "系统菜单接口")
@CrossOrigin
public class TbSysMenuController {
    @Resource
    private TbSysMenuService tbSysMenuService;

    /**
     * 查询所有的部门，后期改造成根据pid排序的
     * @return List<TbSysUser> 返回用户列表
     */
    @ApiOperation(value = "菜单树信息",notes = "全量查询菜单树信息")
    @GetMapping("/tree")
    public Result getDeptTree(){
        List<TbSysMenu> list = tbSysMenuService.list();

        return Result.ok().data("result",true).data("records",list);
    }


}

