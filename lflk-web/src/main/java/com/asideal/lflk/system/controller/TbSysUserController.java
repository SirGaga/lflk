package com.asideal.lflk.system.controller;


import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysUserService;
import com.asideal.lflk.system.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/system/user")
@CrossOrigin
public class TbSysUserController {

    @Resource
    private TbSysUserService tbSysUserService;

    /**
     * 查询所有用户
     * @return List<TbSysUser> 返回用户列表
     */
    @ApiOperation(value = "用户列表",notes = "全量查询用户信息")
    @PostMapping("/")
    public Result findUserAll(@RequestParam(required = true,defaultValue = "1")Integer current,
                              @RequestParam(required = true,defaultValue = "20")Integer size,
                              @RequestBody UserVo userVo){
        System.out.println(userVo);
        // 对用户进行分页，泛型中注入的就是返回数据的实体
        Page<TbSysUser> page = new Page<>(current,size);
        IPage<TbSysUser> userPage = tbSysUserService.page(page, getQueryWrapper(userVo));

        return Result.ok().data("total",userPage.getTotal()).data("records",userPage.getRecords());
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

    public QueryWrapper<TbSysUser> getQueryWrapper(UserVo userVo){
        QueryWrapper<TbSysUser> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(userVo)){
            // 判断部门编码
            if (StringUtils.isNotEmpty(userVo.getDeptCode())){
                queryWrapper.eq("dept_code",userVo.getDeptCode());
            }
            // 判断公民身份郑号
            if (StringUtils.isNotEmpty(userVo.getGmsfhm())){
                queryWrapper.like("gmsfhm",userVo.getGmsfhm());
            }
            // 判断警号
            if (StringUtils.isNotEmpty(userVo.getJh())){
                queryWrapper.like("jh",userVo.getJh());
            }
            // 判断用户姓名
            if (StringUtils.isNotEmpty(userVo.getRealName())){
                queryWrapper.like("real_name",userVo.getRealName());
            }
            // 判断用户状态
            if (ObjectUtils.isNotEmpty(userVo.getStatus())){
                queryWrapper.eq("status",userVo.getStatus());
            }
        }
        return queryWrapper;
    }

}

