package com.asideal.lflk.system.controller;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.websocket.WebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "WebSocket操作接口", tags = "WebSocket操作接口")
@RestController
@RequestMapping("/websocketCtrl")
@EnableScheduling
@Log4j2
public class WebSocketController {
    @Resource
    private WebSocket webSocket;

    @GetMapping("/sendAllWebSocket")
    @ApiOperation(value = "WebSocket群体发送")
    public Result sendAllWebSocket() {
        String messsge="你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(messsge);
        return Result.ok().data("message",messsge);
    }

    @GetMapping("/sendOneWebSocket/{userName}")
    @ApiOperation(value = "WebSocket给指定用户发送消息")
    public Result sendOneWebSocket(@PathVariable("userName") String userName) {
        String messsge=userName+" 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(userName,messsge);
        return Result.ok().data("message",messsge);
    }
    //@Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public Result sendAllWebSocket1() {
        JSONObject server = new JSONObject();
        server.put("cpu", NumberUtil.round(Math.random()*100,2));
        server.put("memory",NumberUtil.round(Math.random()*100,2));
        server.put("disk",NumberUtil.round(Math.random()*100,2));

        JSONObject rabbit = new JSONObject();
        rabbit.put("connectionNum",NumberUtil.round(Math.random()*100,0));
        rabbit.put("channelNum",NumberUtil.round(Math.random()*100,0));
        rabbit.put("exchangeNum",NumberUtil.round(Math.random()*100,0));
        rabbit.put("queueNum",NumberUtil.round(Math.random()*100,0));

        JSONObject message = new JSONObject();
        message.put("server",server);
        message.put("rabbit",rabbit);

        webSocket.sendAllMessage(message.toJSONString());
        //log.info("WebSocket消息发送成功");
        return Result.ok().message("WebSocket消息发送成功");
    }

}
