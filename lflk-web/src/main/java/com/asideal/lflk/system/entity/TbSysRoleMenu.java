package com.asideal.lflk.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 系统权限菜单表
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysRoleMenu对象", description="系统权限菜单表")
public class TbSysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    @ApiModelProperty(value = "是否删除 是否删除，0逻辑未删除，1逻辑已删除")
    private Integer deleted;


}
