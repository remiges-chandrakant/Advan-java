package tech.remiges.workshop.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.remiges.workshop.Constants;

@Service
public class RabbitMqService {

    @Autowired
    RabbitTemplate temprab;

    public void sendMsg(String msg) {

        temprab.convertAndSend(Constants.QUEUE_RABBIT, msg);
    }
}
