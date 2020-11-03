package com.asideal.lflk.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TbSysRole implements Serializable {

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

    @ApiModelProperty(value = "创建人id 创建人id")
    private Integer createUserId;

    @ApiModelProperty(value = "创建人名称 创建人姓名")
    private String createUserName;

    @ApiModelProperty(value = "创建时间 创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人id 更新人id")
    private String updateUserId;

    @ApiModelProperty(value = "更新人姓名 更新人姓名")
    private String updateUserName;

    @ApiModelProperty(value = "最后更新时间 最后更新时间")
    private Date updateTime;


}
