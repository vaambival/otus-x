package ru.otus.otusx.logic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.otusx.dao.FollowerDao;
import ru.otus.otusx.logic.exception.FollowerException;
import ru.otus.otusx.logic.service.FollowerService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private static final String CANNOT_FOLLOW_BY_SELF = "%s try to follow by self";
    private final FollowerDao followerDao;

    @Override
    public void follow(UUID follower, UUID followee) {
        if (Objects.equals(follower, followee)) {
            throw new FollowerException(CANNOT_FOLLOW_BY_SELF);
        }
        followerDao.insertFollower(follower, followee);
    }

    @Override
    public void unFollow(UUID follower, UUID followee) {
        followerDao.deleteFollower(follower, followee);
    }
}
