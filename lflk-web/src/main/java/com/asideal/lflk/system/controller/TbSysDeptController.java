package com.asideal.lflk.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.asideal.lflk.base.controller.BaseController;
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
import java.util.Map;
import java.util.stream.Collectors;

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
public class TbSysDeptController extends BaseController {

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

        return Result.ok().success(true).data("records",list);
    }

    /**
     * 查询所有的部门，后期改造成根据pid排序的
     * @return List<TbSysUser> 返回用户列表
     */
    @ApiOperation(value = "部门信息多个根节点树",notes = "查询部门信息返回多节点树")
    @GetMapping("/multiTree")
    public Result getDeptTreeMultiNode(){
        List<TbSysDept> list = tbSysDeptService.list();

        Map<Integer,List<TbSysDept>> pidListMap =
                list.stream().collect(Collectors.groupingBy(TbSysDept::getParentId));
        list.stream().forEach(item->item.setChildren(pidListMap.get(item.getId())));

        return Result.ok().success(true).data("records", JSON.toJSON(pidListMap.get(0)));
    }

    @ApiOperation(value = "查询单个部门", notes = "通过部门id查询对应的部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/{id}")
    public Result findDeptById(@PathVariable Integer id){
        TbSysDept tbSysDept = tbSysDeptService.getById(id);
        if (ObjectUtil.isNotNull(tbSysDept)){
            return Result.ok().success(true).data("records",tbSysDept);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_NOT_EXIST.getCode(),ResultCode.DEPARTMENT_NOT_EXIST.getMessage());
        }
    }
    @ApiOperation(value = "更新部门", notes = "根据部门的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, dataType = "Integer")
    })
    @PutMapping("/update/{id}")
    public Result updateDeptById(@PathVariable Integer id, @RequestBody TbSysDept tbSysDept) {
        log.info("更新部门===>部门id："+id);
        prepareUpdateInfo(tbSysDept);
        boolean b = tbSysDeptService.updateById(tbSysDept);
        if (b) {
            return Result.ok().success(true).data("records",tbSysDept);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_UPDATE_FAILURE.getCode(),ResultCode.DEPARTMENT_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "添加部门" ,notes = "根据部门实体添加部门")
    @PostMapping("/add")
    public Result saveDept(@RequestBody TbSysDept tbSysDept){
        prepareSaveInfo(tbSysDept);
        boolean b = tbSysDeptService.save(tbSysDept);
        if (b) {
            return Result.ok().success(true).data("records",tbSysDept);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_ADD_FAILURE.getCode(),ResultCode.DEPARTMENT_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除部门" ,notes = "根据部门id删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/delete/{id}")
    public Result deleteDeptById(@PathVariable Integer id){
        boolean b = tbSysDeptService.removeById(id);
        if (b) {
            return Result.ok().success(true);
        } else {
            throw new BusinessException(ResultCode.DEPARTMENT_DELETE_FAILURE.getCode(),ResultCode.DEPARTMENT_DELETE_FAILURE.getMessage());
        }
    }
}

