package com.asideal.lflk.system.controller;


import cn.hutool.core.collection.CollUtil;
import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysUserRole;
import com.asideal.lflk.system.service.TbSysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/system/userRole")
@Api(value = "系统用户管理模块",tags = "系统用户接口")
@CrossOrigin
public class TbSysUserRoleController {

    @Resource
    private TbSysUserRoleService tbSysUserRoleService;

    @ApiOperation(value = "查询角色ID", notes = "通过用户id查询对应的角色id信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @PostMapping({"/{userId}"})
    public Result getRolesByUserId(@PathVariable Integer userId){
        QueryWrapper<TbSysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",userId);
        List<TbSysUserRole> userRoles = tbSysUserRoleService.list(sysUserRoleQueryWrapper);

        if (CollUtil.isNotEmpty(userRoles)) {
            return Result.ok().data("result",true).data("records",userRoles);
        } else {
            return Result.error().code(ResultCode.ROLE_NOT_ASSIGNED.getCode()).message(ResultCode.ROLE_NOT_ASSIGNED.getMessage()).data("result",false);
        }
    }
    @ApiOperation(value = "添加用户角色关系", notes = "通过用户角色关系集合添加数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbSysUserRoles", value = "用户角色集合", required = true, dataType = "List<TbSysUserRole>")
    })
    @PostMapping("/add")
    public Result add(@RequestBody List<TbSysUserRole> tbSysUserRoles){
        boolean b = tbSysUserRoleService.saveBatch(tbSysUserRoles);
        if (b) {
            return Result.ok().data("result",true);
        }else {
            return Result.error().code(ResultCode.USER_ROLE_ADD_FAILURE.getCode()).message(ResultCode.USER_ROLE_ADD_FAILURE.getMessage()).data("result",false);
        }
    }
    @ApiOperation(value = "修改用户角色关系", notes = "通过用户角色关系集合修改数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbSysUserRoles", value = "用户角色集合", required = true, dataType = "List<TbSysUserRole>")
    })
    @PostMapping("/update")
    public Result update(@RequestBody List<TbSysUserRole> tbSysUserRoles){

        boolean b = tbSysUserRoleService.removeByIds(tbSysUserRoles.stream().map(TbSysUserRole::getId).collect(Collectors.toList()));
        if (b){
            b = tbSysUserRoleService.saveBatch(tbSysUserRoles);
            if (b) {
                return Result.ok().data("result",true);
            }else {
                throw new BusinessException(ResultCode.USER_ROLE_ADD_FAILURE.getCode(),ResultCode.USER_ROLE_ADD_FAILURE.getMessage());
            }
        } else {
            throw new BusinessException(ResultCode.USER_ROLE_UPDATE_FAILURE.getCode(),ResultCode.USER_ROLE_UPDATE_FAILURE.getMessage());
        }
    }
}

