package com.asideal.lflk.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * RabbitMQ消息实体类
 * @author MengTianYou
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TbMessage消息", description="消息表")
public class TbMessage implements Serializable {

    @ApiModelProperty(value = "消息ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "预警ID")
    private String yjid;
    @ApiModelProperty(value = "消息内容")
    private String messageBody;
    @ApiModelProperty(value = "入库时间")
    private Date rksj;
    @ApiModelProperty(value = "是否删除，0代表未删除（默认值），1代表已删除")
    private Integer deleted;
}
