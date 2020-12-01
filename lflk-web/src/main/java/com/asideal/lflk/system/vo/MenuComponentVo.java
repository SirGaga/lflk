package com.asideal.lflk.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单实体转成菜单组件Vo
 * @author family
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ApiModel(value="菜单组件对象", description="将菜单信息转成菜单组件信息")
public class MenuComponentVo {

    @ApiModelProperty(value = "菜单ID 菜单ID")
    private Integer id;

    @ApiModelProperty(value = "前端keep-alive属性设置")
    private String name;

    @ApiModelProperty(value = "菜单名称 菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父级菜单id 父级菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单内排序 菜单内排序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否是子菜单 0代表不是子菜单，1代表是子菜单")
    private Integer child;

    @ApiModelProperty(value = "菜单类型 类型，0菜单，1按钮")
    private String type;

    @ApiModelProperty(value = "图标样式 内联样式")
    private String icon;

    @ApiModelProperty(value = "是否可用 0可用，1不可用")
    private Integer disabled;

    @ApiModelProperty(value = "是否打开 0不展开，1展开")
    private String open;

    @ApiModelProperty(value = "是否隐藏，是否隐藏，0代表不隐藏，1代表隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "是否隐藏，是否隐藏，0代表不隐藏，1代表隐藏")
    private String component;

    @ApiModelProperty(value = "前端路径")
    private String path;

    @ApiModelProperty(value = "固钉，0代表不是固钉，1代表是固钉")
    private Integer affix;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "子组件集合")
    private List<MenuComponentVo> children;

    @ApiModelProperty(value = "组件Meta属性")
    private MetaVo meta;

}
