package ru.otus.otusx.logic.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String jwt) {
        this.token = jwt;
    }
}
