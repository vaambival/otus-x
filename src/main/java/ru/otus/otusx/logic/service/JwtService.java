package ru.otus.otusx.logic.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUser(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
