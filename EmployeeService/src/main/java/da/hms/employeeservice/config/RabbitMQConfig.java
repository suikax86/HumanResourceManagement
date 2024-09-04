package da.hms.employeeservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        rabbitTemplate.setReplyTimeout(1500); // Set reply timeout to 1.5 seconds
        return rabbitTemplate;
    }

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
    public Queue employeeRewardRequestQueue() {
        return QueueBuilder.durable("employeeRewardRequestQueue")
                .withArgument("x-message-ttl", 1500) // TTL set to 1.5 seconds
                .build();
    }

    @Bean
    public Queue employeeTransferPointsQueue() {
        return new Queue("employeeTransferPointsQueue");
    }

    @Bean
    public Binding bindingEmployeeCreatedQueue(@Qualifier("employeeCreatedQueue") Queue employeeCreatedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeCreatedQueue).to(employeeExchange).with("employee.created");
    }

    @Bean
    public Binding bindingEmployeeDeletedQueue(@Qualifier("employeeDeletedQueue") Queue employeeDeletedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeDeletedQueue).to(employeeExchange).with("employee.deleted");
    }

    @Bean
    public Binding bindingEmployeeRewardRequestQueue(@Qualifier("employeeRewardRequestQueue") Queue employeeRewardRequestQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeRewardRequestQueue).to(employeeExchange).with("employee.reward.request");
    }

    @Bean
    public Binding bindingEmployeeTransferPointsQueue(@Qualifier("employeeTransferPointsQueue") Queue employeeTransferPointsQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeTransferPointsQueue).to(employeeExchange).with("employee.points.transfer");
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange("emailExchange");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue");
    }

    @Bean
    public Binding bindingEmailQueue(@Qualifier("emailQueue") Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("email.sent");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}