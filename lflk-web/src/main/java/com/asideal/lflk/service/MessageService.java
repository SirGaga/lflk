package com.asideal.lflk.service;

import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.system.service.RabbitMqService;
import com.asideal.lflk.websocket.WebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 */
@Api(value = "对外消息服务模块", tags = "提供对外消息服务模块")
@RestController
@RequestMapping("/service")
@CrossOrigin//是用来处理跨域请求的注解
@Log4j2
public class MessageService {
    @Autowired
    private RabbitMqService rabbitMqService;
    //交换机名称
    @Value("${app.rabbitmq.exchange.ali2me-normal-exchange}")
    private String ali2meNormalExchange;
    /**
     * topic路由key
     */
    @Value("${app.rabbitmq.key.ali2me-normal-routing-key}")
    private String ali2meNormalRoutingKey;
    //交换机名称
    @Value("${app.rabbitmq.exchange.server2me-normal-exchange}")
    private String server2meNormalExchange;

    @PostMapping("/receiveAli2MeMessage")
    @ApiOperation(value = "接收阿里推送的预警消息")
    public Result receiveAli2MeMessageByTopic(@ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByTopic(ali2meNormalExchange,ali2meNormalRoutingKey,message);
        return Result.ok();
    }

    @PostMapping("/receiveServerMessage")
    @ApiOperation(value = "接收服务器运行参数消息")
    public Result receiveServerMessageByFanout(@ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByFanout(server2meNormalExchange,message);
        return Result.ok();
    }
}
