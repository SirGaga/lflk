package com.asideal.lflk.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 组件中的meta属性
 * </p>
 *
 * @author ZhangJie
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysMenuMeta对象", description="组件中的meta属性")
@JsonIgnoreProperties(value={"id", "menuId"})
public class TbSysMenuMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组件title")
    private String title;

    @ApiModelProperty(value = "固钉，0代表非固钉，1代表固钉")
    private Boolean affix;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单id")
    private Integer menuId;


}
