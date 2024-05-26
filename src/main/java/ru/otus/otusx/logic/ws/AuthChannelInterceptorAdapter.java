package ru.otus.otusx.logic.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.otus.otusx.logic.service.JwtService;
import ru.otus.otusx.web.security.JwtAuthenticationFilter;

import static ru.otus.otusx.web.security.JwtAuthenticationFilter.BEARER_PREFIX;

@Component
@RequiredArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            var authentication = accessor.getFirstNativeHeader(JwtAuthenticationFilter.HEADER_NAME);

            var jwt = authentication.substring(BEARER_PREFIX.length());
            var uuid = jwtService.extractUser(jwt);
            var details = userDetailsService.loadUserByUsername(uuid);
            if (jwtService.isTokenValid(jwt, details)) {
                var authToken = new UsernamePasswordAuthenticationToken(details, null,
                        details.getAuthorities());
                accessor.setUser(authToken);
            }
        }
        return message;
    }
}
