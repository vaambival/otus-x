package ru.otus.otusx.logic.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.otus.otusx.logic.exception.IsNotAuthenticated;
import ru.otus.otusx.logic.service.UserSecurityService;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Override
    public UUID getUuid() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            throw new IsNotAuthenticated("Token is invalid");
        }
        var token = (User) auth.getPrincipal();
        return UUID.fromString(token.getUsername());
    }
}
