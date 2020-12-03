package com.asideal.lflk.listener;

import com.alibaba.fastjson.JSONObject;
import com.asideal.lflk.system.entity.TbMessage;
import com.asideal.lflk.system.service.impl.RabbitMqServiceImpl;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Log4j2
public class RabbitMqServiceListener {
    @Resource
    private RabbitMqServiceImpl rabbitService;
    /**
     * 正常监听并处理阿里推送队列消息
     * @param msgObj
     * @param message
     * @param channel
     *
     @RabbitListener(bindings = {
     @QueueBinding(
     value = @Queue(
     value = "ali2me-normal-queue", durable = "false", exclusive = "false", autoDelete = "true",
     arguments = {
     //声明该队列的死信消息发送到的 交换机 （队列添加了这个参数之后会自动与该交换机绑定，并设置路由键，不需要开发者手动设置)
     @Argument(name = "x-dead-letter-exchange", value = "ali2me-fail-exchange"),
     //声明该队列死信消息在交换机的 路由键ali2me-fail-routing-key，这个是死值
     @Argument(name = "x-dead-letter-routing-key", value = "ali2me-fail-routing-key")
     }
     ),
     exchange = @Exchange(value = "ali2me-normal-exchange",type = "topic",autoDelete = "true"),
     key = {"#.info.#"}
     )
     })
     */
    @RabbitListener(bindings = {
        @QueueBinding(
            value = @Queue(
                value = "${app.rabbitmq.queue.ali2me-normal-queue}", durable = "false", exclusive = "false", autoDelete = "true",
                arguments = {
                    //声明该队列的死信消息发送到的 交换机 （队列添加了这个参数之后会自动与该交换机绑定，并设置路由键，不需要开发者手动设置)
                    @Argument(name = "x-dead-letter-exchange", value = "${app.rabbitmq.exchange.ali2me-fail-exchange}"),
                    //声明该队列死信消息在交换机的 路由键ali2me-fail-routing-key，这个是死值
                    @Argument(name = "x-dead-letter-routing-key", value = "${app.rabbitmq.key.ali2me-fail-routing-key}")
                }
            ),
            exchange = @Exchange(value = "${app.rabbitmq.exchange.ali2me-normal-exchange}",type = "topic",autoDelete = "true"),
            key = {"${app.rabbitmq.key.ali2me-normal-routing-key}"}
        )
    })
    public void receiveAli2MeMessage(JSONObject msgObj, Message message, Channel channel) {
        log.info("--------------------------------------------------------------消息处理开始--------------------------------------------------------------");
        log.info("接收到阿里推送的数据包：{}", msgObj);
        if (!msgObj.isEmpty() && msgObj.containsKey("yjid")) {
            try {
                String yjid = msgObj.getString("yjid");
                //通过yjid判断是哪个地市的预警数据
                String dsname=msgObj.containsKey("datasource")?msgObj.getString("datasource"):"master";
                log.info("数据源{}",dsname);
                //数据源名称
                DynamicDataSourceContextHolder.push(dsname);
                // your code 需注意使用后一定要使用poll清空数据源，
                TbMessage tbMessage = new TbMessage();
                tbMessage.setYjid(msgObj.getString("yjid")).setMessageBody(msgObj.toJSONString()).setRksj(new Date());
                rabbitService.save(tbMessage);
            } catch (Exception e) {
                log.error("{}", e.toString());
                throw e;
            } finally {
                DynamicDataSourceContextHolder.poll();
            }
            log.info("--------------------------------------------------------------消息处理完成--------------------------------------------------------------");
        }else{
            log.error("阿里推送的无效数据包：{}", message);
            throw new RuntimeException("阿里推送的无效数据包异常");
        }
    }

    /**
     * 当处理阿里推送消息异常时，进行手动处理
     * @param msgObj
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = {
        @QueueBinding(
            value = @Queue(
                    value = "${app.rabbitmq.queue.ali2me-fail-queue}", durable = "false", exclusive = "false", autoDelete = "true"
            ),
            exchange = @Exchange(value = "${app.rabbitmq.exchange.ali2me-fail-exchange}",type = "topic",autoDelete = "true"),
            key = {"${app.rabbitmq.key.ali2me-fail-routing-key}"}
        )
    })
    public void receiveAli2MeMessageForDead(JSONObject msgObj, Message message, Channel channel) {
        log.info("接收到死信消息并自动签收:{}", msgObj);
    }
    /*
    @RabbitListener(queuesToDeclare = @Queue(value = "ali2me_yj_info", durable = "true", exclusive = "false", autoDelete = "false"))
    public void recevieByAli2Me(@Payload JSONObject message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel, Message message1) {
        MessageProperties messageProperties = message1.getMessageProperties();
        List<Map<String, ?>> xDeathHeader = messageProperties.getXDeathHeader();
        log.info("{}",xDeathHeader);
        // 获取转发重试次数
        long count = messageProperties.getDeliveryTag();
        log.info("次数{}",count);

        if (count >= 3) {
            ////拒绝消息
            try {
                rabbitService.sendMessageByWork("ali2me_yj_fail",message);
            } catch (Exception e) {
                log.error("{}",e.toString());
            }
        } else {
            log.info("重置次数{}", messageProperties.getDeliveryTag());
            log.info("接收到阿里推送的数据包：{}", message);
            if (!message.isEmpty() && message.containsKey("yjid")) {
                String yjid = message.getString("yjid");
                //通过yjid判断是哪个地市的预警数据
                DynamicDataSourceContextHolder.push("master");//数据源名称
                Object obj = null;
                try {
                    // your code 需注意使用后一定要使用poll清空数据源，
                    log.info("111代理开始{}", message);
                    TbMessage tbMessage = new TbMessage();
                    tbMessage.setYjid("111111").setMessageBody(message.toJSONString()).setRksj(new Date());
                    rabbitService.saveOrUpdate(tbMessage);
                    log.info("111代理结束");
                    if (message.getString("abc").equals("111")) {
                        int num = 0;
                        int a = 1 / num;
                    }
                    //确认消息
                    *//**
                     * 无异常就确认消息
                     * basicAck(long deliveryTag, boolean multiple)
                     * deliveryTag:取出来当前消息在队列中的的索引;
                     * multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
                     * deliveryTag为5及其以下的消息;一般设置为false
                     *//*
                    channel.basicAck(deliveryTag, false);
                } catch (Exception e) {
                    log.error("{}", e.toString());
                    //否认消息
                    try {
                        *//**
                         * 有异常就绝收消息
                         * basicNack(long deliveryTag, boolean multiple, boolean requeue)
                         * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
                         *         false:将消息丢弃
                         *//*
                        //channel.basicNack(deliveryTag, false, true);
                        channel.basicReject(deliveryTag, false);
                    } catch (IOException ex) {
                        log.error("{}", ex.toString());
                    }
                    try {
                        log.info("3秒休息之后再继续");
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                    }
                } finally {
                    DynamicDataSourceContextHolder.poll();
                }
            }else{
                log.error("阿里推送的无效数据包：{}", message);
            }
        }
    }

    //@RabbitListener(queuesToDeclare = @Queue(value = "ali2me_yj_info", durable = "true", exclusive = "false", autoDelete = "false"))
    public void recevieByAli2Me2(@Payload JSONObject message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("接收到阿里推送的数据包：{}", message);
        if (!message.isEmpty() && message.containsKey("yjid")) {
            String yjid = message.getString("yjid");
            //通过yjid判断是哪个地市的预警数据
            *//*
            AdminServiceCglibProxy proxyFactory = new AdminServiceCglibProxy(rabbitService);
            RabbitServiceImpl proxy=(RabbitServiceImpl)proxyFactory.getProxyInstance();
            TbMessage tbMessage= new TbMessage();
            tbMessage.setYjid("111111").setMessageBody("消息内容").setRksj(new Date());
            proxy.save(tbMessage);
            *//*
            DynamicDataSourceContextHolder.push("master");//数据源名称
            Object obj = null;
            try {
                // your code 需注意使用后一定要使用poll清空数据源，
                log.info("222代理开始{}", message);
                TbMessage tbMessage = new TbMessage();
                tbMessage.setYjid("111111").setMessageBody(message.toJSONString()).setRksj(new Date());
                rabbitService.saveOrUpdate(tbMessage);
                log.info("222代理结束");
                //确认消息
                channel.basicAck(deliveryTag, false);
            } catch (Exception e) {
                log.error("{}", e.toString());
                //否认消息
                try {
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException ex) {
                    log.error("{}", ex.toString());
                }
            } finally {
                DynamicDataSourceContextHolder.poll();
            }
        } else {
            log.error("阿里推送的无效数据包：{}", message);
        }
    }
*/
    /*
    @RabbitListener(queuesToDeclare = @Queue(value = "queue_work", durable = "false", exclusive = "false", autoDelete = "true"))
    public void processWork1(JSONObject message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("work消费者1接收到的信息："+message);
    }
    @RabbitListener(queuesToDeclare = @Queue(value = "queue_work", durable = "false", exclusive = "false", autoDelete = "true"))
    public void processWork2(JSONObject message) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("work消费者2接收到的信息："+message);
    }
    //-----------------------------------------------------------------------------------
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_fanout",type = "fanout",autoDelete = "true")//绑定交换机
            )
    })
    public void processFanout1(JSONObject message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("fanout广播式消费者1接收到的信息："+message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_fanout",type = "fanout",autoDelete = "true")//绑定交换机
            )
    })
    public void processFanout2(JSONObject message) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("fanout广播式消费者2接收到的信息："+message);
    }
    //-----------------------------------------------------------------------------------
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_logs",type = "direct",autoDelete = "true"),//绑定交换机
                    key = {"warn","info","error"}
            )
    })
    public void processDirect1(JSONObject message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("direct路由式消费者1接收到的信息："+message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_logs",type = "direct",autoDelete = "true"),//绑定交换机
                    key = {"error"}
            )
    })
    public void processDirect2(JSONObject message) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("direct路由式消费者2接收到的信息："+message);
    }

    //-----------------------------------------------------------------------------------
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_topic",type = "topic",autoDelete = "true"),//绑定交换机
                    key = {"*.info.*"}
            )
    })
    public void processTopic1(JSONObject message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("topic订阅式消费者1接收到的信息：{}",message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不写名字就是创建临时队列
                    exchange = @Exchange(value = "ex_topic",type = "topic",autoDelete = "true"),//绑定交换机
                    key = {"#.error.#"}
            )
    })
    public void processTopic2(JSONObject message) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("topic订阅式消费者2接收到的信息：{}",message);
    }
    */
}
