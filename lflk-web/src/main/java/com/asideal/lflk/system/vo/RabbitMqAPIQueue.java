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
@ApiModel(value="RabbitMQ队列信息", description="RabbitMQ队列信息 ")
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMqAPIQueue {
    @ApiModelProperty(value = "虚拟主机")
    private String vhost;
    @ApiModelProperty(value = "队列名称")
    private String name;
    @ApiModelProperty(value = "消息总个数")
    private String messages;
    @ApiModelProperty(value = "等待消费消息个数")
    private String messages_ready;
    @ApiModelProperty(value = "正在消费消息个数")
    private String messages_unacknowledged;
    @ApiModelProperty(value = "队列是否持久化")
    private String durable;
    @ApiModelProperty(value = "队列是否持久化")
    private String exclusive;
    @ApiModelProperty(value = "队列是否自动删除")
    private String auto_delete;
    @ApiModelProperty(value = "状态")
    private String state;
}
