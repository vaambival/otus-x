package ru.otus.otusx.logic.exception;

import org.springframework.security.core.AuthenticationException;

public class IsNotAuthenticated extends AuthenticationException {

    public IsNotAuthenticated(String msg) {
        super(msg);
    }
}
