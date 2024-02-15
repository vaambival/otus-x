package ru.otus.otusx.logic.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.otusx.dao.entity.User;
import ru.otus.otusx.logic.dto.UserDto;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;

@Component
public class UserMapper {

    private final ModelMapper mapper = new ModelMapper();

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User toEntity(UserRegistrationRequest request) {
        return mapper.map(request, User.class);
    }
}
