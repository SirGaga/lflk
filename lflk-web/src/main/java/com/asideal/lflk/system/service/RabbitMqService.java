package com.asideal.lflk.system.service;

import com.asideal.lflk.system.entity.TbMessage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RabbitMqService extends IService<TbMessage> {
    public void sendMessageByWork(String queueName,Object message);
    public void sendMessageByFanout(String exchange,Object message);
    public void sendMessageByDirect(String exchange,String routingKey,Object message);
    public void sendMessageByTopic(String exchange,String routingKey,Object message);
    /**
     * 创建exchange交换机
     * 注意事项：不能amq开头
     *
     * @param exchangeName 交换机名称
     * @param durable      持久化：存入磁盘，重启还在
     * @param autoDelete   自动删除：至少有一个queue或者exchange和该exchange绑定，否则自动删除
     */
    public void createExchange(String exchangeName, boolean durable, boolean autoDelete);

    /**
     * 删除exchange交换机
     *
     * @param exchangeName 交换机名称
     */
    public void deleteExchange(String exchangeName);

    /**
     * 创建queue队列
     *
     * @param queueName  队列名称
     * @param durable    持久化
     * @param exclusive  是否独占
     * @param autoDelete 自动删除
     */
    public void createQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete);

    /**
     * 删除队列
     *
     * @param queueName 队列名称
     */
    public void deleteQueue(String queueName);

    /**
     * 绑定
     *
     * @param queueName    队列名称
     * @param exchangeName 交换机名称
     * @param routingKey   路由键
     */
    public void bind(String queueName, String exchangeName, String routingKey);
}
