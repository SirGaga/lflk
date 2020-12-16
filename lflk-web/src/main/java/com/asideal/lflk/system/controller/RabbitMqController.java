package com.asideal.lflk.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.system.service.RabbitMqService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * RabbitMq控制器
 *
 * @author MengTianYou
 * @since 2020-12-01
 */
@Api(value = "MQ监控及相关操作模块", tags = "MQ监控及操作接口")
@RestController
@RequestMapping("/system/rabbit")
@CrossOrigin//是用来处理跨域请求的注解
@Log4j2
public class RabbitMqController {
    @Autowired
    private RabbitMqService rabbitMqService;

    @PostMapping("/sendMessageByWork")
    @ApiOperation(value = "MQ发送消息[工作模式/一对一]")
    public Result sendMessageByWork(@ApiParam(value="消息队列名称",required=true)@RequestParam String queueName,
                                    @ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByWork(queueName,message);
        return Result.ok();
    }

    @PostMapping("/sendMessageByFanout")
    @ApiOperation(value = "MQ发送消息[发布/订阅模式(广播式)]",notes ="发布/订阅模式(广播式)")
    public Result sendMessageByFanout(@ApiParam(value="交换机名称",required=true)@RequestParam String exchange,
                                      @ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByFanout(exchange,message);
        return Result.ok();
    }
    @PostMapping("/sendMessageByDirect")
    @ApiOperation(value = "MQ发送消息[路由模式Routing]",notes ="路由模式Routing")
    public Result sendMessageByDirect(@ApiParam(value="交换机名称",required=true)@RequestParam String exchange,
                                      @ApiParam(value="路由名称",required=true)@RequestParam String routingKey,
                                      @ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByDirect(exchange,routingKey,message);
        return Result.ok();
    }
    @PostMapping("/sendMessageByTopic")
    @ApiOperation(value = "MQ发送消息[Topics通配符模式]",notes ="Topics（通配符模式）")
    public Result sendMessageByTopic(@ApiParam(value="交换机名称",required=true)@RequestParam String exchange,
                                     @ApiParam(value="通配符名称",required=true)@RequestParam String routingKey,
                                     @ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByTopic(exchange,routingKey,message);
        return Result.ok();
    }
}