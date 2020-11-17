package com.asideal.lflk.system.controller;


import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.asideal.lflk.system.service.TbSysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统权限菜单表 前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/system/roleMenu")
public class TbSysRoleMenuController {
    @Resource
    private TbSysRoleMenuService tbSysRoleMenuService;

    @ApiOperation(value = "根据角色id获取菜单id", notes = "根据角色id获取菜单id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer")
    })
    @PostMapping("/{roleId}")
    public Result getRoleMenusByRoleId(@PathVariable Integer roleId) {
        QueryWrapper<TbSysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<TbSysRoleMenu> tbSysRoleMenus = tbSysRoleMenuService.list(queryWrapper);
        if (tbSysRoleMenus != null){
            return Result.ok().data("result",true).data("records",tbSysRoleMenus);
        } else {
            return Result.ok().code(ResultCode.MENU_NOT_ASSIGNED.getCode()).message(ResultCode.MENU_NOT_ASSIGNED.getMessage()).data("result",false);
        }
    }
    @ApiOperation(value = "根据角色id获取更新角色菜单", notes = "根据角色id更新角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String")
    })
    @PutMapping("/{roleId}")
    public Result updateRoleMenusByRoleId(@PathVariable Integer roleId, @RequestBody String menuIds) {
        QueryWrapper<TbSysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        boolean b = tbSysRoleMenuService.remove(queryWrapper);
        if (b){
            b = tbSysRoleMenuService.saveBatch(Arrays.stream(menuIds.split(",")).map(e -> {
                TbSysRoleMenu tbSysRoleMenu = new TbSysRoleMenu();
                tbSysRoleMenu.setRoleId(roleId);
                tbSysRoleMenu.setRoleId(Integer.parseInt(e));
                return tbSysRoleMenu;
            }).collect(Collectors.toList()));
            if (b){
                return Result.ok().data("result",true);
            } else {
                throw new BusinessException(ResultCode.ROLE_MENU_ADD_FAILURE.getCode(),ResultCode.ROLE_MENU_ADD_FAILURE.getMessage());
            }
        } else {
            // ResultCode.ROLE_MENU_DELETE_FAILURE.getCode()
            // ResultCode.ROLE_MENU_DELETE_FAILURE.getMessage()
            throw new BusinessException(ResultCode.ROLE_MENU_DELETE_FAILURE.getCode(),ResultCode.ROLE_MENU_DELETE_FAILURE.getMessage());
        }
    }


}

