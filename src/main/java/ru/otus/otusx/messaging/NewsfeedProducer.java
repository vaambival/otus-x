package ru.otus.otusx.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.otusx.logic.dto.message.PostDto;
import ru.otus.otusx.messaging.message.NewsfeedMessage;

@Component
@RequiredArgsConstructor
public class NewsfeedProducer {

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.ws.messages}")
    private String messagesQueue;

    private final AmqpTemplate amqpTemplate;

    public void sendNews(NewsfeedMessage message) {
        amqpTemplate.convertAndSend(exchange, "", message);
    }

    public void sendNotifications(PostDto postDto) {
        amqpTemplate.convertAndSend(messagesQueue, postDto);
    }
}
