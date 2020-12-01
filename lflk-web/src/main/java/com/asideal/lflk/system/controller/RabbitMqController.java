package com.asideal.lflk.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.response.Result;
import com.asideal.lflk.response.ResultCode;
import com.asideal.lflk.system.service.RabbitMqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@Log4j2
public class RabbitMqController {

    @Resource
    private Environment env;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.monitorPort}")
    private String port;
    /**
     * 交换机名称
     */
    @Value("${app.rabbitmq.exchange.ali2me-normal-exchange}")
    private String ali2me_normal_exchange;
    /**
     * topic路由key
     */
    @Value("${app.rabbitmq.key.ali2me-normal-routing-key}")
    private String ali2me_normal_routing_key;


    @Resource
    private RabbitMqService rabbitMqService;
    @GetMapping("/overview")
    @ApiOperation(value = "查询总体RabbitMQ运行状态")
    public JSONObject overview() {
        log.info("host="+env.getProperty("spring.rabbitmq.host"));
        JSONObject jsonObj = getData("http://"+host+":"+port+"/api/overview", username, password);
        return jsonObj;
    }

    @PostMapping("/receiveAli2MeMessage")
    @ApiOperation(value = "接收阿里推送的预警消息")
    public Result receiveAli2MeMessageByTopic(@ApiParam(value="json格式",required=true)@RequestBody JSONObject message) {
        rabbitMqService.sendMessageByTopic(ali2me_normal_exchange,ali2me_normal_routing_key,message);
        return Result.ok();
    }
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


    public static void main(String[] args) {
        JSONObject jsonObj = new RabbitMqController().getData("http://192.168.109.121:15672/api/overview", "guest", "guest");
        System.out.println(jsonObj.getString("cluster_name"));
        System.out.println(jsonObj.getJSONObject("object_totals").getString("connections"));
    }
    private JSONObject getData(String url, String username, String password) {
        JSONObject jsonObj = new JSONObject();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
        httpGet.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response =null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != ResultCode.SUCCESS.getCode()) {
                System.out.println("call http api to get rabbitmq data return code: " + response.getStatusLine().getStatusCode() + ", url: " + url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JSONObject.parseObject(EntityUtils.toString(entity));
            }
        } catch (Exception e) {

        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObj;
    }

}

