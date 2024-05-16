package ru.otus.otusx.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.otusx.logic.service.NewsFeedService;
import ru.otus.otusx.messaging.message.NewsfeedMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsfeedListener {

    private final NewsFeedService newsFeedService;

    @Value("${rabbitmq.queue}")
    private String queue;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleMessage(NewsfeedMessage message) {
        log.info("Received message from {}", queue);
        if (message.isDelete()) {
            newsFeedService.deletePost(message.getPost(), message.getFollowers());
        } else {
            newsFeedService.addPost(message.getPost(), message.getFollowers());
        }
    }
}
