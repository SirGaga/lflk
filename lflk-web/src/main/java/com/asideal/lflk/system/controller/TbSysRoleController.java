package com.asideal.lflk.system.controller;


import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysRole;
import com.asideal.lflk.system.service.TbSysRoleService;
import com.asideal.lflk.system.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/system/role")
@Api(value = "系统权限管理模块",tags = "系统权限接口")
@CrossOrigin
public class TbSysRoleController {
    @Resource
    private TbSysRoleService tbSysRoleService;

    /**
     * 查询所有角色
     * @return  返回角色列表
     */
    @ApiOperation(value = "角色列表", notes = "全量查询角色信息")
    @PostMapping("/")
    public Result findRoleAll(@RequestBody RoleVo roleVo){
        // 对用户进行分页，泛型中注入的就是返回数据的实体
        Page<TbSysRole> page = new Page<>(roleVo.getCurrent(),roleVo.getSize());
        IPage<TbSysRole> rolePage = tbSysRoleService.page(page, getQueryWrapper(roleVo));

        return Result.ok().data("total",rolePage.getTotal()).data("records",rolePage.getRecords());
    }

    @PostMapping("/add")
    public Result addRole(@RequestBody TbSysRole role){
        boolean b = tbSysRoleService.save(role);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.ROLE_ADD_FAILURE.getCode(),ResultCode.ROLE_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "更新角色", notes = "根据角色的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Integer")
    })
    @PutMapping("/{id}")
    public Result updateUserById(@PathVariable Integer id, @RequestBody TbSysRole tbSysRole) {
        boolean b = tbSysRoleService.updateById(tbSysRole);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.ROLE_UPDATE_FAILURE.getCode(),ResultCode.ROLE_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除角色" ,notes = "根据角色id删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/{id}")
    public Result deleteUserById(@PathVariable Integer id){
        boolean b = tbSysRoleService.removeById(id);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.ROLE_DELETE_FAILURE.getCode(),ResultCode.ROLE_DELETE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "判断角色是否存在" ,notes = "根据角色英文判断角色是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @PostMapping("/exist")
    public Result roleExist(@RequestBody RoleVo roleVo){
        QueryWrapper<TbSysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleVo.getRoleName());
        int count = tbSysRoleService.count(queryWrapper);
        return count >= 0 ? Result.ok().data("result", true) : Result.ok().data("result", false);
    }

    public QueryWrapper<TbSysRole> getQueryWrapper(RoleVo roleVo){
        QueryWrapper<TbSysRole> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(roleVo)){
            // 判断部门编码
            if (StringUtils.isNotEmpty(roleVo.getRoleName())){
                queryWrapper.eq("role_name",roleVo.getRoleName());
            }
            // 判断公民身份郑号
            if (StringUtils.isNotEmpty(roleVo.getRoleNameCh())){
                queryWrapper.like("role_name_ch",roleVo.getRoleNameCh());
            }
        }
        return queryWrapper;
    }

}

