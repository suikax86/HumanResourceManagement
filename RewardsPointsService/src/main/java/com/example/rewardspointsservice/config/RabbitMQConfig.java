package com.example.rewardspointsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return new Queue("employeeCreatedQueue");
    }

    @Bean
    public Queue employeeDeletedQueue() {
        return new Queue("employeeDeletedQueue");
    }

    @Bean
    public Binding binding(@Qualifier("employeeCreatedQueue") Queue employeeAddedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeAddedQueue).to(employeeExchange).with("employee.created");
    }

    @Bean
    public Binding bindingEmployeeDeletedQueue(@Qualifier("employeeDeletedQueue") Queue employeeDeletedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeDeletedQueue).to(employeeExchange).with("employee.deleted");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
