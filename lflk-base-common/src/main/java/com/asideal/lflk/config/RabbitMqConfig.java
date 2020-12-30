package com.asideal.lflk.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq消息队列配置
 * @author MengTianYou
 */
@Configuration
@Log4j2
public class RabbitMqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        //Mandatory为true时,消息通过交换器无法匹配到队列会返回给生产者，为false时匹配不到会直接被丢弃
        rabbitTemplate.setMandatory(true);
        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.error("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if (!ack) {
                log.error("消息发送到RabbitMQ失败,ID: {},内容: {}",correlationData.getId(),new String(correlationData.getReturnedMessage().getBody()));
                log.error("消息发送到RabbitMQ失败,ID: {},原因: {}",correlationData.getId(), cause);
            } else {
                //log.info("消息发送到RabbitMQ成功,id: {}{}", correlationData.getId(),new String(correlationData.getReturnedMessage().getBody()));
            }
        });
        return rabbitTemplate;
    }
}
