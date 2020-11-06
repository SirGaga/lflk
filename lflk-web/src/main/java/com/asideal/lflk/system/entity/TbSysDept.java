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
 * 部门表 
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysDept对象", description="部门表 ")
public class TbSysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门编号 部门编号")
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    @ApiModelProperty(value = "部门编码 部门编码")
    private String deptCode;

    @ApiModelProperty(value = "部门名称 部门名称")
    private String deptName;

    @ApiModelProperty(value = "父部门编号 父部门编号")
    private Integer deptParentId;

    @ApiModelProperty(value = "父部门编码 父部门编码")
    private String deptParentCode;

    @ApiModelProperty(value = "父部门名称 父部门名称")
    private String deptParentName;

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

    @ApiModelProperty(value = "是否删除，0代表未删除（默认值），1代表已删除")
    private Integer deleted;


}
