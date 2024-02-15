package ru.otus.otusx.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;
import ru.otus.otusx.logic.service.UserService;
import ru.otus.otusx.logic.dto.UserDto;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public UserDto getUser(@PathVariable(name = "id") UUID uuid) {
        return userService.getUser(uuid);
    }

    @PostMapping("/register")
    public UUID register(@RequestBody @Valid UserRegistrationRequest request) {
        return userService.register(request);
    }
}
