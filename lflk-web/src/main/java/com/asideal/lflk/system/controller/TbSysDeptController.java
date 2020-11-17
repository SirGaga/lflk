package com.asideal.lflk.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysDept;
import com.asideal.lflk.system.service.TbSysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
@Log4j2
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
    @ApiOperation(value = "查询单个用户", notes = "通过用户id查询对应的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/{id}")
    public Result findDeptById(@PathVariable Integer id){
        TbSysDept tbSysDept = tbSysDeptService.getById(id);
        if (ObjectUtil.isNotNull(tbSysDept)){
            return Result.ok().data("result",true).data("records",tbSysDept);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }
    }
    @ApiOperation(value = "更新部门", notes = "根据部门的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, dataType = "Integer")
    })
    @PutMapping("/{id}")
    public Result updateDeptById(@PathVariable Integer id, @RequestBody TbSysDept tbSysDept) {
        log.info("更新部门===>部门id："+id);
        boolean b = tbSysDeptService.updateById(tbSysDept);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_UPDATE_FAILURE.getCode(),ResultCode.DEPARTMENT_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "添加部门" ,notes = "根据部门实体添加部门")
    @PostMapping("/add")
    public Result saveUser(@RequestBody TbSysDept tbSysDept){
        boolean b = tbSysDeptService.save(tbSysDept);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_ADD_FAILURE.getCode(),ResultCode.DEPARTMENT_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除部门" ,notes = "根据部门id删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/{id}")
    public Result deleteDeptById(@PathVariable Integer id){
        boolean b = tbSysDeptService.removeById(id);
        if (b) {
            return Result.ok().data("result", true);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_DELETE_FAILURE.getCode(),ResultCode.DEPARTMENT_DELETE_FAILURE.getMessage());
        }
    }
}

