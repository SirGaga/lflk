package com.asideal.lflk.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@ApiModel(value="RabbitMQ监控信息", description="RabbitMQ监控信息 ")
public class RabbitMqAPIInfo {
    @ApiModelProperty(value = "连接信息")
    private List<RabbitMqAPIConnection> conns;
    @ApiModelProperty(value = "通道信息")
    private List<RabbitMqAPIChannel> channels;
    @ApiModelProperty(value = "交换机信息")
    private List<RabbitMqAPIExchange> exchanges;
    @ApiModelProperty(value = "队列信息")
    private List<RabbitMqAPIQueue> queues;

    @ApiModelProperty(value = "RabbitMQ版本")
    private String rabbitmq_version;
    @ApiModelProperty(value = "erlang版本")
    private String erlang_version;
    @ApiModelProperty(value = "消息总个数")
    private String messages;
    @ApiModelProperty(value = "等待消费消息个数")
    private String messages_ready;
    @ApiModelProperty(value = "正在消费消息个数")
    private String messages_unacknowledged;


    @ApiModelProperty(value = "连接个数")
    private int connectionNum;
    @ApiModelProperty(value = "通道个数")
    private int channelNum;
    @ApiModelProperty(value = "交换机个数")
    private int exchangeNum;
    @ApiModelProperty(value = "队列个数")
    private int queueNum;
    @ApiModelProperty(value = "客户端个数")
    private int consumerNum;
}