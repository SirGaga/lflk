package com.asideal.lflk.system.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
/*import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;*/
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Api(value = "RabbbitMQ监控及操作管理模块", tags = "RabbbitMQ监控及操作接口")
@RestController
@RequestMapping("/system/rabbitMqMonitor")
@CrossOrigin//是用来处理跨域请求的注解
public class RabbitMqMonitorControl {
    @Resource
    private Environment env;
    @GetMapping("/overview")
    public Map<String, String> overview() {
        //JSONObject jsonObj = getData("http://192.168.109.121:15672/api/overview", "guest", "guest");
        //jsonObj.getString("");
        return null;
    }
/*

    public static void main(String[] args) {
        JSONObject jsonObj = new RabbitMqMonitorControl().getData("http://192.168.109.121:15672/api/overview", "guest", "guest");
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
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("call http api to get rabbitmq data return code: " + response.getStatusLine().getStatusCode() + ", url: " + url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JSONObject.parseObject(EntityUtils.toString(entity));
            }
        } catch (Exception e) {

        } finally {
            try {
                if(response!=null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObj;
    }
*/

}

