package ru.otus.otusx.logic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.otusx.dao.UserDao;
import ru.otus.otusx.logic.dto.UserDto;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;
import ru.otus.otusx.logic.exception.NotFoundException;
import ru.otus.otusx.logic.mapper.UserMapper;
import ru.otus.otusx.logic.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDto getUser(UUID uuid) {
        var user = userDao.findByUuid(uuid);
        if (user.isEmpty()) {
            throw new NotFoundException("User with uuid " + uuid + " is not found");
        }
        return userMapper.toDto(user.get());
    }

    @Override
    public UUID register(UserRegistrationRequest request) {
        var user = userMapper.toEntity(request)
                .setPassword(encoder.encode(request.getPassword()))
                .setUuid(UUID.randomUUID());
        return userDao.save(user);
    }

    @Override
    public List<UserDto> search(String name, String surname) {
        var users = userDao.findByPrefixNames(name, surname);
        return userMapper.toDtos(users);
    }
}
