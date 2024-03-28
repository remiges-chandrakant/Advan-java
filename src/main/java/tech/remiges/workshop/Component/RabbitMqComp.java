package tech.remiges.workshop.Component;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import tech.remiges.workshop.Constants;

@Configuration
public class RabbitMqComp {

    @Bean
    public Queue myQueue() {
        return new Queue(Constants.QUEUE_RABBIT, false);
    }

    @Bean
    public Queue mysQueue() {
        return QueueBuilder.durable("my_queue").autoDelete().exclusive().build();
    }

    @RabbitListener(queues = Constants.QUEUE_RABBIT)
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }

    // Queue w = new QueueBuilder().maxLength(1).autoDelete().exclusive().build();

}
