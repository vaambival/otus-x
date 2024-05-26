package ru.otus.otusx.logic.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionListener implements ApplicationListener<SessionSubscribeEvent> {

    private final RegisterClientService registerClientService;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        log.info("Subscribe {} user", event.getUser().getName());
        registerClientService.register(event.getUser().getName());
    }
}
