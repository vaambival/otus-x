package ru.otus.otusx.logic.service;

import ru.otus.otusx.logic.dto.UserDto;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;

import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID uuid);

    UUID register(UserRegistrationRequest request);
}
