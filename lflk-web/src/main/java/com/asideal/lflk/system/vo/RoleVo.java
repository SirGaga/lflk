package com.asideal.lflk.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="角色查询对象", description="角色查询对象")
public class RoleVo {

    @ApiModelProperty(value = "姓名 真实姓名")
    @JsonProperty("roleName")
    private String roleName;

    @ApiModelProperty(value = "警号 警号")
    @JsonProperty("roleNameCh")
    private String roleNameCh;

    @ApiModelProperty(value = "每页显示条数")
    @JsonProperty("size")
    private Integer size;

    @ApiModelProperty(value = "当前页")
    @JsonProperty("current")
    private Integer current;

}
