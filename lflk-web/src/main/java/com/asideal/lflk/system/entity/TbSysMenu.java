package com.asideal.lflk.system.entity;

import com.asideal.lflk.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单表 
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysMenu对象", description="菜单表 ")
public class TbSysMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID 菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单名称 菜单名称")
    private String name;

    @ApiModelProperty(value = "父级菜单id 父级菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "跳转url 跳转路径")
    private String path;

    @ApiModelProperty(value = "菜单内排序 菜单内排序")
    private int orderNum;

    @ApiModelProperty(value = "菜单类型 类型，0目录，1菜单，2按钮")
    private String type;

    @ApiModelProperty(value = "图标样式 内联样式")
    private String icon;

    @ApiModelProperty(value = "是否可用 0可用，1不可用")
    private Integer disabled;

    @ApiModelProperty(value = "是否删除，0代表逻辑未删除（默认值），1代表逻辑已删除")
    private Integer deleted;

    @ApiModelProperty(value = "是否隐藏，是否隐藏，0代表不隐藏，1代表隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "是否隐藏，是否隐藏，0代表不隐藏，1代表隐藏")
    private String component;

    @ApiModelProperty(value = "子目录、子菜单")
    @TableField(exist = false)
    private List<TbSysMenu> children;

    @ApiModelProperty(value = "组件属性")
    @TableField(exist = false)
    private TbSysMenuMeta meta;

    @ApiModelProperty(value = "组件重定向属性")
    @TableField(exist = false)
    private String redirect;

    @ApiModelProperty(value = "树组件需要的树形")
    @TableField(exist = false)
    private String label;

    @ApiModelProperty(value = "meta属性需要的属性")
    @TableField(exist = false)
    private String title;

    @ApiModelProperty(value = "meta属性需要的属性")
    @TableField(exist = false)
    private String affix;

//    @ApiModelProperty(value = "是否包含子菜单")
//    @TableField(exist = false)
//    private Boolean hasChildren;


}
