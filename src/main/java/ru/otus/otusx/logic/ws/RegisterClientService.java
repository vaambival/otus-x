package ru.otus.otusx.logic.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegisterClientService {
    private final AmqpTemplate amqpTemplate;

    public void register(String name) {
        amqpTemplate.convertAndSend("subscriptions", name);
    }
}
