package ru.otus.otusx.logic.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.otusx.logic.dto.request.LoginRequest;
import ru.otus.otusx.logic.dto.response.JwtAuthenticationResponse;
import ru.otus.otusx.logic.facade.LoginService;
import ru.otus.otusx.logic.service.JwtService;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getId(),
                request.getPassword()
        ));
        var user = userDetailsService.loadUserByUsername(request.getId().toString());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
