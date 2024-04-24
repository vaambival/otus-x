package ru.otus.otusx.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.otusx.dao.FeedCacheDao;
import ru.otus.otusx.dao.FollowerDao;
import ru.otus.otusx.dao.UserDao;
import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.logic.service.NewsFeedService;
import ru.otus.otusx.messaging.NewsfeedProducer;
import ru.otus.otusx.messaging.message.NewsfeedMessage;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsFeedServiceImpl implements NewsFeedService {
    private final FollowerDao followerDao;
    private final UserDao userDao;
    private final NewsfeedProducer newsfeedProducer;
    private final FeedCacheDao feedCacheDao;

    @Override
    @Async
    public void addPost(UUID author, Post post) {
        log.info("Add post {} of author {}", post.getUuid(), author);
        if (userDao.isCelebrity(author)) {
            return;
        }
        newsfeedProducer.sendNews(new NewsfeedMessage(followerDao.findAllFollowers(author), post, false));
    }

    @Override
    @Async
    public void deletePost(UUID author, UUID post) {
        log.info("Delete post {} of author {}", post, author);
        if (userDao.isCelebrity(author)) {
            return;
        }
        newsfeedProducer.sendNews(
                new NewsfeedMessage(followerDao.findAllFollowers(author), new Post().setUuid(post), true));
    }

    @Override
    public void deletePost(Post post, List<UUID> followers) {
        log.info("Delete from cache {}", post.getUuid());
        feedCacheDao.delete(post, followers);
    }

    @Override
    public void addPost(Post post, List<UUID> followers) {
        log.info("Put in cache {}", post.getUuid());
        feedCacheDao.add(post, followers);
    }

    @Override
    public List<Post> getFeed(UUID user) {
        return feedCacheDao.getFeed(user);
    }

    @Scheduled(fixedRateString = "${spring.scheduler.newsfeed-rate}")
    public void invalidateNewsfeed() {
        userDao.findAllUuids().forEach(feedCacheDao::invalidate);
    }
}
