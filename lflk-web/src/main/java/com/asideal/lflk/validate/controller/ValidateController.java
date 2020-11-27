package com.asideal.lflk.validate.controller;


import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.security.service.AuthenticationService;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.service.TbSysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Api(value = "系统用户管理模块",tags = "系统用户接口")
@RestController
@RequestMapping("/validate")
@CrossOrigin
@Log4j2
public class ValidateController {

    @Resource
    private TbSysMenuService tbSysMenuService;

    @Resource
    private TbSysUserService tbSysUserService;

    @Resource
    private AuthenticationService authenticationService;

    @ApiOperation(value = "监测用户名是否重复" ,notes = "根据用户填写的用户名进行校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    })
    @GetMapping("/checkUserName")
    public Result checkUserName(@RequestParam String userName){

        QueryWrapper<TbSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        int count = tbSysUserService.count(queryWrapper);
        return count > 0 ?
                Result.ok()
                        .success(false)
                        .message(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage())
                : Result.ok().success(true);

    }

    @ApiOperation(value = "监测身份证号是否重复" ,notes = "根据用户填写的公民身份号码进行校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gmsfhm", value = "公民身份号码", required = true, dataType = "String")
    })
    @GetMapping("/checkGmsfhm")
    public Result checkGmsfhm(@RequestParam String gmsfhm){

        QueryWrapper<TbSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gmsfhm", gmsfhm);
        int count = tbSysUserService.count(queryWrapper);
        return count > 0 ?
                Result.ok()
                        .success(false)
                        .message(ResultCode.USER_GMSFHM_ALREADY_EXIST.getMessage())
                : Result.ok().success(true);

    }

}

