package com.asideal.lflk.system.controller;


import com.asideal.lflk.base.controller.BaseController;
import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysRole;
import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.asideal.lflk.system.service.TbSysRoleMenuService;
import com.asideal.lflk.system.service.TbSysRoleService;
import com.asideal.lflk.system.vo.CrudVo;
import com.asideal.lflk.system.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
public class TbSysRoleController extends BaseController {
    @Resource
    private TbSysRoleService tbSysRoleService;

    @Resource
    private TbSysRoleMenuService tbSysRoleMenuService;

    /**
     * 查询所有角色
     * @return  返回角色列表
     */
    @ApiOperation(value = "角色列表", notes = "全量查询角色信息")
    @PostMapping("/")
    public Result findRoleAll(@RequestBody RoleVo roleVo){
        // 对用户进行分页，泛型中注入的就是返回数据的实体
        Page<TbSysRole> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        IPage<TbSysRole> rolePage = tbSysRoleService.findRoleMenuByPage(page, getQueryWrapper(roleVo));

        return Result.ok().success(true).data("total",rolePage.getTotal()).data("records",rolePage.getRecords());
    }

    @ApiModelProperty(value = "通过角色获取所有的菜单", notes = "通过角色id获取所有的菜单id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Integer")
    })
    @PostMapping("/roleMenu/{id}")
    public Result getMenuIdsByRoleId(@PathVariable Integer id){
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(id);
        List<TbSysRoleMenu> roleMenus = tbSysRoleMenuService.selectByRoleIds(roleIds);
        return Result.ok().success(true).data("records",roleMenus);
    }

    @ApiOperation(value = "新增角色", notes = "根据角色实体新增角色")
    @PostMapping("/add")
    public Result addRole(@RequestBody TbSysRole role){
        try{
            prepareSaveInfo(role);
            boolean b = tbSysRoleService.save(role);
            return Result.ok().success(true);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ROLE_ADD_FAILURE.getCode(),ResultCode.ROLE_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "更新角色", notes = "根据角色的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Integer")
    })
    @PutMapping("/update/{id}")
    public Result updateUserById(@PathVariable Integer id, @RequestBody TbSysRole role) {
        try {
            prepareUpdateInfo(role);
            boolean b = tbSysRoleService.updateById(role);
            return Result.ok().success(true);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ROLE_UPDATE_FAILURE.getCode(),ResultCode.ROLE_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除角色" ,notes = "根据角色id删除角色")
    @DeleteMapping("/delete")
    public Result deleteRoleById(@RequestBody CrudVo crudVo){
        try {
            tbSysRoleService.removeByIds(crudVo.getIds());
            tbSysRoleMenuService.remove(new LambdaQueryWrapper<TbSysRoleMenu>().in(TbSysRoleMenu::getRoleId,crudVo.getIds()));
            return Result.ok().success(true);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ROLE_DELETE_FAILURE.getCode(),ResultCode.ROLE_DELETE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "判断角色是否存在" ,notes = "根据角色英文判断角色是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Integer")
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
        queryWrapper.eq("deleted",0);
        if (ObjectUtils.isNotEmpty(roleVo)){
            if (StringUtils.isNotEmpty(roleVo.getNameFilter())){
                queryWrapper.like("role_name", roleVo.getNameFilter()).or().like("role_name_ch", roleVo.getNameFilter());
            }
        }
        return queryWrapper;
    }

}

