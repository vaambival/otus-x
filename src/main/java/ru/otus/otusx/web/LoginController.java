package ru.otus.otusx.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.otusx.logic.dto.request.LoginRequest;
import ru.otus.otusx.logic.dto.response.JwtAuthenticationResponse;
import ru.otus.otusx.logic.facade.LoginService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public JwtAuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }
}
