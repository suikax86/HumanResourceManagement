package da.hms.employeeservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public Binding bindingEmployeeCreatedQueue(@Qualifier("employeeCreatedQueue") Queue employeeCreatedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeCreatedQueue).to(employeeExchange).with("employee.created");
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