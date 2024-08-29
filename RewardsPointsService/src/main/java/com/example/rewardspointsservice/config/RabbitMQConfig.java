package com.example.rewardspointsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange("employeeExchange");
    }

    @Bean
    public Queue employeeCreatedQueue() {
        return new Queue("employeeCreatedQueue",false);
    }

    @Bean
    public Binding binding(Queue employeeAddedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeAddedQueue).to(employeeExchange).with("employee.created");
    }
}


