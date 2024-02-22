package ru.otus.otusx.logic.service;

import ru.otus.otusx.logic.dto.UserDto;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID uuid);

    UUID register(UserRegistrationRequest request);

    List<UserDto> search(String name, String surname);
}
