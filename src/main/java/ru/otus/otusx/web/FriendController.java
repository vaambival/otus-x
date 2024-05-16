package ru.otus.otusx.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.otusx.logic.service.FollowerService;
import ru.otus.otusx.logic.service.UserSecurityService;

import java.util.UUID;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendController {

    private final FollowerService followerService;
    private final UserSecurityService userSecurityService;

    @PutMapping("/set/{user_id}")
    public void addUser(@PathVariable("user_id") UUID userUuid) {
        followerService.follow(userSecurityService.getUuid(), userUuid);
    }

    @PutMapping("/delete/{user_id}")
    public void deleteUser(@PathVariable("user_id") UUID userUuid) {
        followerService.unFollow(userSecurityService.getUuid(), userUuid);
    }
}
