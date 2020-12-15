package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单VO
 * @author ZhangJie
 */

@Data
@ApiModel(value="角色菜单VO", description="用于接收前台传递过来的用户角色关系")
public class RoleMenuVo {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID的集合")
    private List<Integer> menuIds;
}
