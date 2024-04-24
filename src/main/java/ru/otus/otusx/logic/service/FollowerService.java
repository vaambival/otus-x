package ru.otus.otusx.logic.service;

import java.util.UUID;

public interface FollowerService {
    void follow(UUID follower, UUID followee);

    void unFollow(UUID follower, UUID followee);
}
