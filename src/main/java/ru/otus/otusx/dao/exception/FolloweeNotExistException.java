package ru.otus.otusx.dao.exception;

import java.util.UUID;

public class FolloweeNotExistException extends RuntimeException {
    private UUID followee;

    public FolloweeNotExistException(UUID followee) {
        super("User with uuid %s doesn't exist".formatted(followee));
        this.followee = followee;
    }
}
