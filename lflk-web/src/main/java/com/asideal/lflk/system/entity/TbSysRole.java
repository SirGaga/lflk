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

/**
 * <p>
 * 角色表 
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysRole对象", description="角色表 ")
public class TbSysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID 角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名 角色名")
    private String roleName;

    @ApiModelProperty(value = "角色中文 角色名中文")
    private String roleNameCh;

    @ApiModelProperty(value = "角色描述 角色描述")
    private String roleRemark;

    @ApiModelProperty(value = "是否删除 是否删除，0逻辑未删除，1逻辑已删除")
    private Integer deleted;

    @ApiModelProperty(value = "菜单id集合")
    @TableField(exist = false)
    private String menuId;

}
