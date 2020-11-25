package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="用户查询对象", description="用户查询对象")
public class UserVo {

    @ApiModelProperty(value = "姓名 真实姓名")
    private String realName;

    @ApiModelProperty(value = "警号 警号")
    private String jh;

    @ApiModelProperty(value = "公民身份号码 公民身份号码")
    private String gmsfhm;

    @ApiModelProperty(value = "部门名称 部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编码 部门编码，遵守GAB规定")
    private String deptCode;

    @ApiModelProperty(value = "是否可用，0不可用，1可用")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    private Integer userName;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "每页显示条数")
    private Integer size;

}
