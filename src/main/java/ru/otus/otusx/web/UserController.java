package ru.otus.otusx.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.otusx.logic.dto.UserDto;
import ru.otus.otusx.logic.dto.request.UserRegistrationRequest;
import ru.otus.otusx.logic.service.UserSecurityService;
import ru.otus.otusx.logic.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserSecurityService userSecurityService;

    @GetMapping("/get/{id}")
    public UserDto getUser(@PathVariable(name = "id") UUID uuid) {
        log.info("Get user by id {}", uuid);
        return userService.getUser(uuid);
    }

    @PostMapping("/register")
    public UUID register(@RequestBody @Valid UserRegistrationRequest request) {
        log.info("Register user");
        return userService.register(request);
    }

    @GetMapping("/search")
    public List<UserDto> search(@RequestParam("first_name") String name, @RequestParam("last_name") String surname) {
        log.info("Search user by {} {}", name, surname);
        return userService.search(name, surname);
    }

    @GetMapping
    public UUID getUser() {
        log.info("Auth user");
        return userSecurityService.getUuid();
    }
}
