package com.asideal.lflk.system.controller;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.vo.MenuComponentVo;
import com.asideal.lflk.system.vo.MetaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ApiOperation(value = "获取组件", notes = "通过角色信息获取可使用的组件")
    @PostMapping("/components")

    public Result getComponent(@RequestBody List<String> roleNames){
        List<TbSysMenu> components = tbSysMenuService.getComponentByRoleNames(roleNames);
        if(CollUtil.isNotEmpty(components)){
            List<MenuComponentVo> voList = JSON.parseArray(JSON.toJSONString(components), MenuComponentVo.class);
            //以pid为Key进行分组存入Map
            Map<Integer,List<MenuComponentVo>> pidListMap =
                    voList.stream().collect(Collectors.groupingBy(MenuComponentVo::getParentId));
            voList.stream().forEach(item->item.setChildren(pidListMap.get(item.getId())));
            System.out.println(pidListMap.get(0));
            pidListMap.get(0).forEach(e -> {
                MetaVo metaVo = new MetaVo();
                String component = e.getComponent();
                if (CollUtil.isNotEmpty(e.getChildren())){
                    String suffix = e.getChildren().stream().filter(p -> p.getOrderNum() == 1).map(MenuComponentVo::getPath).collect(Collectors.toList()).get(0);
                    e.setRedirect(e.getPath()+suffix);
                    String extraName = e.getPath().substring(1).substring(0, 1).toUpperCase() + e.getPath().substring(1).substring(1);
                    e.setName(extraName);
                    metaVo.setTitle(extraName);
                    metaVo.setIcon(e.getIcon());
                    metaVo.setAffix(e.getAffix() == 1);

                } else {

                    e.setComponent("Layout");
                    e.setRedirect(component);
                    List<MenuComponentVo> childrenList = new ArrayList<>();
                    MenuComponentVo children = new MenuComponentVo();
                    children.setPath(component);
                    children.setComponent(component+"/index");
                    metaVo.setTitle(e.getMenuName());
                    metaVo.setIcon(e.getIcon());
                    metaVo.setAffix(e.getAffix() == 1);
                    children.setMetaVo(metaVo);
                    childrenList.add(children);
                    e.setChildren(childrenList);
                }
            });
            //取出顶层节点的对象，数据库中的顶层节点的"ParentId"为0,注意是ParentId
            return Result.ok().data("result",true).data("records",voList);
        } else {
            return Result.error()
                    .code(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getCode())
                    .message(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getMessage())
                    .data("result",false);
        }
    }

    @ApiOperation(value = "菜单树信息",notes = "全量查询菜单树信息")
    @GetMapping("/tree")
    public Result getRoleTree(){
        List<TbSysMenu> list = tbSysMenuService.list();

        return Result.ok().data("result",true).data("records",list);
    }

    @ApiOperation(value = "新增菜单", notes = "根据菜单实体新增菜单")
    @PostMapping("/add")
    public Result addMenu(@RequestBody TbSysMenu menu){
        boolean b = tbSysMenuService.save(menu);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.MENU_ADD_FAILURE.getCode(),ResultCode.MENU_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "更新菜单", notes = "根据菜单的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Integer")
    })
    @PutMapping("/{id}")
    public Result updateMenu(@PathVariable Integer id, @RequestBody TbSysMenu menu) {
        boolean b = tbSysMenuService.updateById(menu);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.MENU_UPDATE_FAILURE.getCode(),ResultCode.MENU_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除菜单" ,notes = "根据菜单id删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/{id}")
    public Result deleteMenuById(@PathVariable Integer id){
        boolean b = tbSysMenuService.removeById(id);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.MENU_DELETE_FAILURE.getCode(),ResultCode.MENU_DELETE_FAILURE.getMessage());
        }
    }

}

