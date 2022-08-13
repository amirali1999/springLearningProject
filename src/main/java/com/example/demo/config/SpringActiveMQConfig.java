package com.example.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.util.Arrays;

@Configuration
public class SpringActiveMQConfig {
    @Value("${activemq.broker-url}")
    public String brokerUrl;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("amirali.queue");
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setTrustAllPackages(true);
        return factory;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
