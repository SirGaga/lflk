package com.asideal.lflk.system.controller;

import com.asideal.lflk.system.vo.RabbitMqAPIChannel;
import com.asideal.lflk.system.vo.RabbitMqAPIConnection;
import com.asideal.lflk.system.vo.RabbitMqAPIExchange;
import com.asideal.lflk.system.vo.RabbitMqAPIInfo;
import com.asideal.lflk.system.vo.RabbitMqAPIQueue;
import com.asideal.lflk.system.service.RabbitMqApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "MQ监控API接口", tags = "MQ监控API接口")
@RestController
@RequestMapping("/system/rabbit")
@CrossOrigin//是用来处理跨域请求的注解
@Log4j2
public class RabbitMqApiController {
    @Autowired
    private RabbitMqApiService service;

    @GetMapping("/api/overview")
    @ApiOperation(value = "查询总体RabbitMQ运行状态")
    public RabbitMqAPIInfo overview() {
        return service.overview();
    }

    @GetMapping("/api/connections")
    @ApiOperation(value = "RabbitMQ连接信息")
    public List<RabbitMqAPIConnection> connections() {
        return service.connections();
    }

    @GetMapping("/api/channels")
    @ApiOperation(value = "RabbitMQ通道信息")
    public List<RabbitMqAPIChannel> channels() {
        return service.channels();
    }

    @GetMapping("/api/exchanges")
    @ApiOperation(value = "RabbitMQ交换机信息")
    public List<RabbitMqAPIExchange> exchanges() {
        return service.exchanges();
    }

    @GetMapping("/api/queues")
    @ApiOperation(value = "RabbitMQ队列信息")
    public List<RabbitMqAPIQueue> queues() {
        return service.queues();
    }
}

