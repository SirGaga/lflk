package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 前端角色查询对象
 * @author family
 */
@Data
@ApiModel(value="角色查询对象", description="角色查询对象")
public class RoleVo {

    @ApiModelProperty(value = "姓名 真实姓名")
    private String roleName;

    @ApiModelProperty(value = "警号 警号")
    private String roleNameCh;

    @ApiModelProperty(value = "查询条件")
    private String nameFilter;

    @ApiModelProperty(value = "每页显示条数")
    private Integer limit;

    @ApiModelProperty(value = "当前页")
    private Integer page;

}
