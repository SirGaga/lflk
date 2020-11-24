package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="组件meta属性", description="组件中meta属性设置")
public class MetaVo {
    @ApiModelProperty(value = "页面title")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "是否为固钉")
    private Boolean affix;
}
