package com.asideal.lflk.system.controller;

import com.asideal.lflk.handler.BusinessException;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.vo.RabbitMqAPIChannel;
import com.asideal.lflk.system.vo.RabbitMqAPIConnection;
import com.asideal.lflk.system.vo.RabbitMqAPIExchange;
import com.asideal.lflk.system.vo.RabbitMqAPIInfo;
import com.asideal.lflk.system.vo.RabbitMqAPIQueue;
import com.asideal.lflk.system.service.RabbitMqApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author mengty
 * @date 2020-12-03
 */
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
    public RabbitMqAPIInfo overview(){
        try {
            return service.overview();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_READ_FAILURE.getCode(),ResultCode.RABBITMQ_API_READ_FAILURE.getMessage());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_AUTH_FAILURE.getCode(),ResultCode.RABBITMQ_API_AUTH_FAILURE.getMessage());
        }
    }

    @GetMapping("/api/connections")
    @ApiOperation(value = "RabbitMQ连接信息")
    public List<RabbitMqAPIConnection> connections() {
        try {
            return service.connections();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_READ_FAILURE.getCode(),ResultCode.RABBITMQ_API_READ_FAILURE.getMessage());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_AUTH_FAILURE.getCode(),ResultCode.RABBITMQ_API_AUTH_FAILURE.getMessage());
        }
    }

    @GetMapping("/api/channels")
    @ApiOperation(value = "RabbitMQ通道信息")
    public List<RabbitMqAPIChannel> channels() {
        try {
            return service.channels();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_READ_FAILURE.getCode(),ResultCode.RABBITMQ_API_READ_FAILURE.getMessage());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_AUTH_FAILURE.getCode(),ResultCode.RABBITMQ_API_AUTH_FAILURE.getMessage());
        }
    }

    @GetMapping("/api/exchanges")
    @ApiOperation(value = "RabbitMQ交换机信息")
    public List<RabbitMqAPIExchange> exchanges() {
        try {
            return service.exchanges();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_READ_FAILURE.getCode(),ResultCode.RABBITMQ_API_READ_FAILURE.getMessage());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_AUTH_FAILURE.getCode(),ResultCode.RABBITMQ_API_AUTH_FAILURE.getMessage());
        }
    }

    @GetMapping("/api/queues")
    @ApiOperation(value = "RabbitMQ队列信息")
    public List<RabbitMqAPIQueue> queues() {
        try {
            return service.queues();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_READ_FAILURE.getCode(),ResultCode.RABBITMQ_API_READ_FAILURE.getMessage());
        } catch (AuthenticationException e) {
            throw new BusinessException(ResultCode.RABBITMQ_API_AUTH_FAILURE.getCode(),ResultCode.RABBITMQ_API_AUTH_FAILURE.getMessage());
        }
    }
}

