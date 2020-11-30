package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@ApiModel(value="RabbitMQ交换机信息", description="RabbitMQ交换机信息 ")
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMqAPIExchange {
    @ApiModelProperty(value = "虚拟主机")
    private String vhost;
    @ApiModelProperty(value = "交换机名称")
    private String name;
    @ApiModelProperty(value = "交换机类型")
    private String type;
    @ApiModelProperty(value = "交换机是否持久化")
    private String durable;
    @ApiModelProperty(value = "交换机是否自动删除")
    private String auto_delete;
}
