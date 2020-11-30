package com.asideal.lflk.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.system.vo.RabbitMqAPIChannel;
import com.asideal.lflk.system.vo.RabbitMqAPIConnection;
import com.asideal.lflk.system.vo.RabbitMqAPIExchange;
import com.asideal.lflk.system.vo.RabbitMqAPIInfo;
import com.asideal.lflk.system.vo.RabbitMqAPIQueue;
import com.asideal.lflk.system.service.RabbitMqApiService;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RabbitMqApiServiceImpl implements RabbitMqApiService {
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

    @Override
    public RabbitMqAPIInfo overview() {
        RabbitMqAPIInfo info = RabbitMqAPIInfo.builder().build();
        try {
            JSONObject jsonObj = JSONObject.parseObject(getData("http://"+host+":"+port+"/api/overview", username, password));
            info.setConnectionNum(jsonObj.getJSONObject("object_totals").getString("connections"));
            info.setChannelNum(jsonObj.getJSONObject("object_totals").getString("channels"));
            info.setExchangeNum(jsonObj.getJSONObject("object_totals").getString("exchanges"));
            info.setQueueNum(jsonObj.getJSONObject("object_totals").getString("queues"));
            info.setConsumerNum(jsonObj.getJSONObject("object_totals").getString("consumers"));

            info.setMessages(jsonObj.getJSONObject("queue_totals").getString("messages"));
            info.setMessages_ready(jsonObj.getJSONObject("queue_totals").getString("messages_ready"));
            info.setMessages_unacknowledged(jsonObj.getJSONObject("queue_totals").getString("messages_unacknowledged"));

            info.setRabbitmq_version(jsonObj.getString("rabbitmq_version"));
            info.setErlang_version(jsonObj.getString("erlang_version"));

            info.setConns(connections());
            info.setChannels(channels());
            info.setExchanges(exchanges());
            info.setQueues(queues());
        } catch (IOException e) {
            log.error("{}",e);
        }
        return info;
    }
    @Override
    public List<RabbitMqAPIConnection> connections() {
        log.info("host="+env.getProperty("spring.rabbitmq.host"));
        //JSONObject jsonObj = getData("http://"+host+":"+port+"/api/connections", username, password);
        String url="http://"+host+":"+port+"/api/connections";
        List<RabbitMqAPIConnection> list = new ArrayList<>();
        try {
            list = JSONArray.parseArray(getData(url, username, password), RabbitMqAPIConnection.class);
        } catch (IOException e) {
            log.error("{}",e);
        }
        return list;
    }

    @Override
    public List<RabbitMqAPIChannel> channels() {
        String url="http://"+host+":"+port+"/api/channels";
        List<RabbitMqAPIChannel> list = new ArrayList<>();
        try {
            List<JSONObject> arr = JSONArray.parseArray(getData(url, username, password),JSONObject.class);
            arr.forEach((jsonObj)->{
                RabbitMqAPIChannel channel = new RabbitMqAPIChannel();
                channel.setVhost(jsonObj.getString("vhost"));
                channel.setPeer_host(jsonObj.getJSONObject("connection_details").getString("peer_host"));
                channel.setPeer_port(jsonObj.getJSONObject("connection_details").getString("peer_port"));
                channel.setUser(jsonObj.getString("user"));
                channel.setState(jsonObj.getString("state"));
                list.add(channel);
            });
        } catch (IOException e) {
            log.error("{}",e);
        }
        return list;
    }

    @Override
    public List<RabbitMqAPIExchange> exchanges() {
        String url="http://"+host+":"+port+"/api/exchanges";
        List<RabbitMqAPIExchange> list = new ArrayList<>();
        try {
            list = JSONArray.parseArray(getData(url, username, password), RabbitMqAPIExchange.class);
        } catch (IOException e) {
            log.error("{}",e);
        }
        return list;
    }

    @Override
    public List<RabbitMqAPIQueue> queues() {
        String url="http://"+host+":"+port+"/api/queues";
        List<RabbitMqAPIQueue> list = new ArrayList<>();
        try {
            list = JSONArray.parseArray(getData(url, username, password), RabbitMqAPIQueue.class);
        } catch (IOException e) {
            log.error("{}",e);
        }
        return list;
    }
    private String getData(String url, String username, String password) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
        httpGet.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response =null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("call http api to get rabbitmq data return code: " + response.getStatusLine().getStatusCode() + ", url: " + url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if(response!=null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
