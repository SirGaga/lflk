package com.asideal.lflk.system.controller;


import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.service.TbSysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

