package com.asideal.lflk.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="用户查询对象", description="用户查询对象")
public class UserVo {

    @ApiModelProperty(value = "姓名 真实姓名")
    @JsonProperty("realName")
    private String realName;

    @ApiModelProperty(value = "警号 警号")
    @JsonProperty("jh")
    private String jh;

    @ApiModelProperty(value = "公民身份号码 公民身份号码")
    @JsonProperty("gmsfhm")
    private String gmsfhm;

    @ApiModelProperty(value = "部门名称 部门名称")
    @JsonProperty("deptName")
    private String deptName;

    @ApiModelProperty(value = "部门编码 部门编码，遵守GAB规定")
    @JsonProperty("deptCode")
    private String deptCode;

    @ApiModelProperty(value = "是否可用，0不可用，1可用")
    @JsonProperty("status")
    private Integer status;

}
