package com.asideal.lflk.login.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.security.service.AuthenticationService;
import com.asideal.lflk.system.entity.TbSysMenu;
import com.asideal.lflk.system.entity.TbSysUser;
import com.asideal.lflk.system.service.TbSysMenuService;
import com.asideal.lflk.system.service.TbSysUserService;
import com.asideal.lflk.system.vo.ComponentVo;
import com.asideal.lflk.system.vo.MenuComponentVo;
import com.asideal.lflk.system.vo.MetaVo;
import com.asideal.lflk.utils.IpUtils;
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
            List<MenuComponentVo> voList = JSON.parseArray(JSON.toJSONString(components), MenuComponentVo.class);
            //以pid为Key进行分组存入Map
            Map<Integer,List<MenuComponentVo>> pidListMap =
                    voList.stream().collect(Collectors.groupingBy(MenuComponentVo::getParentId));
            voList.stream().forEach(item->item.setChildren(pidListMap.get(item.getId())));
            // 遍历循环将menu中的数据组装成组件信息
            pidListMap.get(0).forEach(e -> {
                MetaVo metaVo = new MetaVo();
                String suffix = e.getChildren().stream().filter(p -> p.getOrderNum() == 1).map(MenuComponentVo::getPath).collect(Collectors.toList()).get(0);
                e.setRedirect((e.getPath()+suffix).replace("//","/"));
                if(e.getPath().length()>1) {
                    String extraName = e.getPath().substring(1).substring(0, 1).toUpperCase() + e.getPath().substring(1).substring(1);
                    e.setName(extraName);
                    metaVo.setTitle(e.getMenuName());
                    metaVo.setIcon(e.getIcon());
                    metaVo.setAffix(e.getAffix() == 1);
                    e.setMeta(metaVo);
                }
                e.getChildren().forEach(c -> {
                    MetaVo metaVoChild = new MetaVo();
                    metaVoChild.setTitle(c.getMenuName());
                    metaVoChild.setIcon(c.getIcon());
                    metaVoChild.setAffix(c.getAffix() == 1);
                    c.setMeta(metaVoChild);
                    String cPath = c.getPath().substring(1);
                    c.setPath(cPath);
                    c.setComponent(cPath + "/index");
                    c.setName(cPath.substring(0,1).toUpperCase() + cPath.substring(1));
                });
            });
            List<ComponentVo> componentList = JSON.parseArray(JSON.toJSONString(pidListMap.get(0)), ComponentVo.class);
            //取出顶层节点的对象，数据库中的顶层节点的"ParentId"为0,注意是ParentId
            return Result.ok().data("result",true).data("records",JSON.toJSON(componentList));
        } else {
            return Result.error()
                    .code(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getCode())
                    .message(ResultCode.MENU_COMPONENT_NOT_ASSIGNED.getMessage())
                    .data("result",false);
        }
    }

}

