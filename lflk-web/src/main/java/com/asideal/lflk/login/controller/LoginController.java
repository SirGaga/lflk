package com.asideal.lflk.login.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.security.service.AuthenticationService;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.entity.TbSysMenuMeta;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysMenuMetaService;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.service.TbSysUserService;
import com.asideal.lflk.system.vo.ComponentVo;
import com.asideal.lflk.utils.IpUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统登录控制器
 *
 * @author ZhangJie
 * @since 2020-11-26
 */
@Api(value = "系统登录管理模块",tags = "系统登录接口")
@RestController
@CrossOrigin
@Log4j2
public class LoginController {

    @Resource
    private TbSysMenuService tbSysMenuService;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private TbSysUserService tbSysUserService;

    @Resource
    private TbSysMenuMetaService tbSysMenuMetaService;

    @ApiOperation(value = "用户信息", notes = "根据用户登录的token获取用户信息")
    @GetMapping("/userInfo")
    private Result getUserInfo(HttpServletRequest request){
        Authentication authentication = authenticationService.getAuthentication();
        TbSysUser tbSysUser = tbSysUserService.getOne(new LambdaUpdateWrapper<TbSysUser>().eq(TbSysUser::getUserName, authentication.getName()));
        LambdaUpdateWrapper<TbSysUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(TbSysUser::getUserName,tbSysUser.getUserName())
                // 获取ip地址
                .set(TbSysUser::getLastLoginIp,IpUtils.getIpAddr(request))
                // 获取更新事件
                .set(TbSysUser::getUpdateTime, DateUtil.date(Calendar.getInstance()))
                // 获取更新用户
                .set(TbSysUser::getUpdateUserName,tbSysUser.getUserName())
                // 获取更新用户id
                .set(TbSysUser::getUpdateUserId,tbSysUser.getId());
        tbSysUserService.update(lambdaUpdateWrapper);
        Object principal = authentication.getPrincipal();
        return Result.ok().data("data",principal);
    }


    @ApiOperation(value = "获取组件", notes = "通过角色信息获取可使用的组件")
    @PostMapping("/components")
    public Result getComponent(@RequestBody List<String> roleNames){
        List<TbSysMenu> components = tbSysMenuService.getComponentByRoleNames(roleNames);
        if(CollUtil.isNotEmpty(components)){
            // 获取menu
            Map<Integer,List<TbSysMenu>> parentIdListMap = components.stream().collect(Collectors.groupingBy(TbSysMenu::getParentId));

            components.stream().forEach(item -> item.setChildren(parentIdListMap.get(item.getId())));

            parentIdListMap.get(0).forEach(e -> {
                String suffix = e.getChildren().stream().filter(p -> p.getOrderNum() == 1).map(TbSysMenu::getPath).collect(Collectors.toList()).get(0);
                e.setRedirect((e.getPath()+suffix).replace("//","/"));
                TbSysMenuMeta meta = tbSysMenuMetaService.getOne(new LambdaQueryWrapper<TbSysMenuMeta>().eq(TbSysMenuMeta::getMenuId, e.getId()));
                if(e.getPath().length()>1) {

                    e.setMeta(meta);
                }
                if (ObjectUtil.isNotEmpty(e.getChildren())) {
                    childComponentFullFill(e.getChildren());
                }
            });

            List<ComponentVo> componentList = JSON.parseArray(JSON.toJSONString(parentIdListMap.get(0)), ComponentVo.class);
            //取出顶层节点的对象，数据库中的顶层节点的"ParentId"为0,注意是ParentId
            System.out.println(JSON.toJSON(componentList));

            return Result.ok().success(true).data("records",JSON.toJSON(componentList));
        } else {
            return Result.error()
                    .code(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getCode())
                    .message(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getMessage())
                    .success(false);
        }
    }

    public void childComponentFullFill(List<TbSysMenu> tbSysMenus) {
        tbSysMenus.forEach(e -> {
            if (ObjectUtil.isNotEmpty(e.getChildren())){
                childComponentFullFill(e.getChildren());
            }
            String ePath = e.getPath().substring(1);
            e.setPath(ePath);
            e.setComponent(ePath + "/index");
            TbSysMenuMeta meta = tbSysMenuMetaService.getOne(new LambdaQueryWrapper<TbSysMenuMeta>().eq(TbSysMenuMeta::getMenuId, e.getId()));
            e.setMeta(meta);

        });
    }

}

