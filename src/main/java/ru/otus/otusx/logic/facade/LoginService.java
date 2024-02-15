package ru.otus.otusx.logic.facade;

import ru.otus.otusx.logic.dto.request.LoginRequest;
import ru.otus.otusx.logic.dto.response.JwtAuthenticationResponse;

public interface LoginService {
    JwtAuthenticationResponse login(LoginRequest request);
}
