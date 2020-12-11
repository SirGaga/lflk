package com.asideal.lflk.system.controller;


import com.alibaba.fastjson.JSON;
import com.asideal.lflk.base.controller.BaseController;
import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.entity.TbSysMenuMeta;
import com.asideal.lflk.system.entity.TbSysRoleMenu;
import com.asideal.lflk.system.service.TbSysMenuMetaService;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.service.TbSysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表  前端控制器
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/system/menu")
@Api(value = "系统菜单管理模块",tags = "系统菜单接口")
@CrossOrigin
public class TbSysMenuController extends BaseController {
    @Resource
    private TbSysMenuService tbSysMenuService;

    @Resource
    private TbSysMenuMetaService tbSysMenuMetaService;

    @Resource
    private TbSysRoleMenuService tbSysRoleMenuService;

    @ApiOperation(value = "菜单树信息",notes = "全量查询菜单树信息")
    @GetMapping("/tree")
    public Result getRoleTree(){
        List<TbSysMenu> list = tbSysMenuService.getMenuTreeTable();

        Map<Integer,List<TbSysMenu>> parentIdListMap = list.stream().collect(Collectors.groupingBy(TbSysMenu::getParentId));

        list.forEach(item -> item.setChildren(parentIdListMap.get(item.getId())));
        return Result.ok().data("records", JSON.toJSON(parentIdListMap.get(0))).success(true);
    }

    @ApiOperation(value = "新增菜单", notes = "根据菜单实体新增菜单")
    @PostMapping("/add")
    public Result addMenu(@RequestBody TbSysMenu menu){
        prepareSaveInfo(menu);
        boolean b = false;
        // 判断是否为菜单，
        if (MENU_TYPE_DIR.equals(menu.getType())) {
            menu.setComponent(MENU_TYPE_DIR_COMPONENT);
            menu.setName(menu.getPath().substring(1).substring(0,1).toUpperCase() + menu.getPath().substring(1).substring(1));
        }
        if (MENU_TYPE_MENU.equals(menu.getType()) || MENU_TYPE_DIR.equals(menu.getType())) {
            TbSysMenuMeta meta = new TbSysMenuMeta();
            meta.setIcon(menu.getIcon());
            meta.setTitle(menu.getTitle());
            meta.setAffix(menu.getAffix().equals(MENU_META_AFFIX));
            b = tbSysMenuService.save(menu);
            if (b) {
                meta.setMenuId(menu.getId());
                b = tbSysMenuMetaService.save(meta);
            }
        } else if (MENU_TYPE_BUTTON.equals(menu.getType())){
            menu.setComponent(null);
            menu.setIcon(null);
            menu.setName(menu.getTitle());
            b = tbSysMenuService.save(menu);
        }
        if (b) {
            return Result.ok().success(true);
        } else {
            throw new BusinessException(ResultCode.MENU_ADD_FAILURE.getCode(),ResultCode.MENU_ADD_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "更新菜单", notes = "根据菜单的id和数据实体进行更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Integer")
    })
    @PutMapping("/update/{id}")
    public Result updateMenu(@PathVariable Integer id, @RequestBody TbSysMenu menu) {
        try {
            if (MENU_TYPE_DIR.equals(menu.getType())) {
                menu.setComponent(MENU_TYPE_DIR_COMPONENT);
                menu.setName(menu.getPath().substring(1).substring(0,1).toUpperCase() + menu.getPath().substring(1).substring(1));
            }
            if (MENU_TYPE_MENU.equals(menu.getType()) || MENU_TYPE_DIR.equals(menu.getType())) {
                TbSysMenuMeta meta = new TbSysMenuMeta();
                meta.setIcon(menu.getIcon());
                meta.setTitle(menu.getTitle());
                meta.setAffix(menu.getAffix().equals(MENU_META_AFFIX));
                meta.setMenuId(menu.getId());
                tbSysMenuMetaService.saveOrUpdate(meta, new LambdaUpdateWrapper<TbSysMenuMeta>().eq(TbSysMenuMeta::getMenuId,menu.getId()));
            } else if (MENU_TYPE_BUTTON.equals(menu.getType())){
                menu.setComponent(null);
                menu.setIcon(null);
                menu.setName(menu.getTitle());
            }
            prepareUpdateInfo(menu);
            tbSysMenuService.updateById(menu);
            return Result.ok().success(true);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.MENU_UPDATE_FAILURE.getCode(),ResultCode.MENU_UPDATE_FAILURE.getMessage());
        }
    }

    @ApiOperation(value = "删除菜单" ,notes = "根据菜单id删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "Integer")
    })
    @DeleteMapping("/delete")
    public Result deleteMenuById(@RequestBody List<Integer> ids){
        // 要考虑到级联删除
        // 必须要先通过id获取获取下边的子菜单也就是parentId为id的数据，
        // 而且删除只考虑本身和他的下级
        boolean b = tbSysMenuService.removeByIds(ids);
        tbSysMenuMetaService.remove(new LambdaQueryWrapper<TbSysMenuMeta>().in(TbSysMenuMeta::getMenuId,ids));
        tbSysRoleMenuService.remove(new LambdaQueryWrapper<TbSysRoleMenu>().in(TbSysRoleMenu::getMenuId,ids));
        if (b) {
            return Result.ok().success(true);
        } else {
            throw new BusinessException(ResultCode.MENU_DELETE_FAILURE.getCode(),ResultCode.MENU_DELETE_FAILURE.getMessage());
        }
    }

}

