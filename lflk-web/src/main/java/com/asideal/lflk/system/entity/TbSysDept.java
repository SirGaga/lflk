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
 * 部门表 
 * </p>
 *
 * @author ZhangJie
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbSysDept对象", description="部门表 ")
public class TbSysDept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门编号 部门编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "部门编码 部门编码")
    private String deptCode;

    @ApiModelProperty(value = "部门名称 部门名称")
    private String deptName;

    @ApiModelProperty(value = "父部门编号 父部门编号")
    private Integer parentId;

    @ApiModelProperty(value = "父部门编码 父部门编码")
    private String parentCode;

    @ApiModelProperty(value = "父部门名称 父部门名称")
    private String parentName;

    @ApiModelProperty(value = "是否删除，0代表未删除（默认值），1代表已删除")
    private Integer deleted;

    @ApiModelProperty(value = "子节点，线性结构转树形结构使用，非数据库字段")
    @TableField(exist = false)
    private List<TbSysDept> children;

}
