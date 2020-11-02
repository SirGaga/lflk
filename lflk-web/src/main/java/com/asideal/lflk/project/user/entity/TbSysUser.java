package com.asideal.lflk.project.user.entity;

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
 * 用户表 
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysUser对象", description="用户表 ")
public class TbSysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID 主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名 真实姓名")
    private String realName;

    @ApiModelProperty(value = "警号 警号")
    private String jh;

    @ApiModelProperty(value = "公民身份号码 公民身份号码")
    private String gmsfhm;

    @ApiModelProperty(value = "部门编号 部门主键")
    private String deptId;

    @ApiModelProperty(value = "部门名称 部门名称")
    private String deptName;

    @ApiModelProperty(value = "最近登录IP 最近登录的ip地址")
    private String lastLoginIp;

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
