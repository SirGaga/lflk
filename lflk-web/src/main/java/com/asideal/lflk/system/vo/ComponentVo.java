package com.asideal.lflk.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 拼装组件的Vo
 * @author ZhangJie
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ApiModel(value="菜单组件对象", description="将菜单信息转成菜单组件信息")
public class ComponentVo {

    @ApiModelProperty(value = "前端keep-alive属性设置")
    private String name;

    @ApiModelProperty(value = "是否隐藏，是否隐藏，0代表不隐藏，1代表隐藏")
    private String component;

    @ApiModelProperty(value = "前端路径")
    private String path;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "子组件集合")
    private List<ComponentVo> children;

    @ApiModelProperty(value = "组件Meta属性")
    private MetaVo meta;

}
