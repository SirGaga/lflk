package com.asideal.lflk.system.service;

import com.asideal.lflk.system.vo.RabbitMqAPIChannel;
import com.asideal.lflk.system.vo.RabbitMqAPIConnection;
import com.asideal.lflk.system.vo.RabbitMqAPIExchange;
import com.asideal.lflk.system.vo.RabbitMqAPIInfo;
import com.asideal.lflk.system.vo.RabbitMqAPIQueue;

import java.util.List;

public interface RabbitMqApiService {
    public RabbitMqAPIInfo overview();
    public List<RabbitMqAPIConnection> connections();
    public List<RabbitMqAPIChannel> channels();
    public List<RabbitMqAPIExchange> exchanges();
    public List<RabbitMqAPIQueue> queues();
}
