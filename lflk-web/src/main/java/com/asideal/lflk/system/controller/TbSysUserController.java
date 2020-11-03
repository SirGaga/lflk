package com.asideal.lflk.system.controller;


import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Api(value = "系统用户管理模块",tags = "系统用户接口")
@CrossOrigin
@RestController
@RequestMapping("/system/user")
public class TbSysUserController {

    @Autowired
    private TbSysUserService tbSysUserService;

    /**
     * 查询所有用户
     * @return List<TbSysUser> 返回用户列表
     */
    @ApiOperation(value = "用户列表",notes = "全量查询用户信息")
    @GetMapping("/")
    @ResponseBody
    public Result findUserAll(){
        List<TbSysUser> list = tbSysUserService.list();
        return Result.ok().data("users",list);
    }

    @ApiOperation(value = "查询单个用户",notes = "通过用户id查询对应的用户信息")
    @GetMapping("/{id}")
    public Result findUserById(@PathVariable Integer id){
        TbSysUser tbSysUser = tbSysUserService.getById(id);
        if (tbSysUser != null){
            return Result.ok().data("user",tbSysUser);
        } else {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    }

}

