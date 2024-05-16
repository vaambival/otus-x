package ru.otus.otusx.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.otusx.messaging.message.NewsfeedMessage;

@Component
@RequiredArgsConstructor
public class NewsfeedProducer {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    private final AmqpTemplate amqpTemplate;

    public void sendNews(NewsfeedMessage message) {
        amqpTemplate.convertAndSend(exchange, "", message);
    }
}
