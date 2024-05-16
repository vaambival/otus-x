package ru.otus.otusx.dao.exception;

import java.util.UUID;

public class AlreadyFollowException extends RuntimeException {
    private UUID follower;
    private UUID followee;

    public AlreadyFollowException(UUID follower, UUID followee) {
        super("User %s is already follower for %s".formatted(follower, followee));
        this.follower = follower;
        this.followee = followee;
    }
}
