package ru.otus.otusx.logic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LoginRequest {

    @NotNull(message = "Необходимо указать UUID пользователя")
    private UUID id;
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

}
