package com.asideal.lflk.system.controller;


import com.asideal.lflk.response.Result;
import com.asideal.lflk.system.entity.TbSysDept;
import com.asideal.lflk.system.service.TbSysDeptService;
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
 * 部门表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Api(value = "系统部门管理模块",tags = "系统部门接口")
@RestController
@RequestMapping("/system/dept")
@CrossOrigin
public class TbSysDeptController {

    @Resource
    private TbSysDeptService tbSysDeptService;
    /**
     * 查询所有的部门，后期改造成根据pid排序的
     * @return List<TbSysUser> 返回用户列表
     */
    @ApiOperation(value = "部门信息",notes = "全量查询部门信息")
    @GetMapping("/tree")
    public Result getDeptTree(){
        List<TbSysDept> list = tbSysDeptService.list();

        return Result.ok().data("records",list);
    }
}

