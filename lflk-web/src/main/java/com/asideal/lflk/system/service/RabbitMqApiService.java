package com.asideal.lflk.system.service;

import com.asideal.lflk.system.vo.RabbitMqAPIChannel;
import com.asideal.lflk.system.vo.RabbitMqAPIConnection;
import com.asideal.lflk.system.vo.RabbitMqAPIExchange;
import com.asideal.lflk.system.vo.RabbitMqAPIInfo;
import com.asideal.lflk.system.vo.RabbitMqAPIQueue;
import org.apache.http.auth.AuthenticationException;

import java.io.IOException;
import java.util.List;

public interface RabbitMqApiService {
    public RabbitMqAPIInfo overview() throws IOException, AuthenticationException;
    public List<RabbitMqAPIConnection> connections() throws IOException, AuthenticationException;
    public List<RabbitMqAPIChannel> channels() throws IOException, AuthenticationException;
    public List<RabbitMqAPIExchange> exchanges() throws IOException, AuthenticationException;
    public List<RabbitMqAPIQueue> queues() throws IOException, AuthenticationException;
}
