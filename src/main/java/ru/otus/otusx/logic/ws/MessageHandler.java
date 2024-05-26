package ru.otus.otusx.logic.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.otus.otusx.logic.dto.message.PostDto;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final SimpMessagingTemplate template;

    @RabbitListener(queues = "${rabbitmq.ws.messages}")
    public void listen(PostDto post) {
        for (var sub : post.getSubscribers()) {
            template.convertAndSendToUser(sub.toString(), "/topic/messages", post);
        }
    }
}
