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
@ApiModel(value="RabbitMQ连接信息", description="RabbitMQ连接信息 ")
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMqAPIConnection {
    @ApiModelProperty(value = "虚拟主机")
    private String vhost;
    @ApiModelProperty(value = "客户端IP")
    private String peer_host;
    @ApiModelProperty(value = "客户端PORT")
    private String peer_port;
    @ApiModelProperty(value = "用户")
    private String user;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "通道个数")
    private String channels;
}
