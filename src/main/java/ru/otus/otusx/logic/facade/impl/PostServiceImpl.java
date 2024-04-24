package ru.otus.otusx.logic.facade.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.otus.otusx.dao.FeedDao;
import ru.otus.otusx.dao.PostDao;
import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.logic.dto.request.PostRequest;
import ru.otus.otusx.logic.dto.request.PostUpdateRequest;
import ru.otus.otusx.logic.exception.IsNotAuthenticated;
import ru.otus.otusx.logic.exception.NotFoundException;
import ru.otus.otusx.logic.mapper.PostMapper;
import ru.otus.otusx.logic.facade.PostService;
import ru.otus.otusx.logic.service.NewsFeedService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final String AUTHOR_NOT_AUTHENTICATED = "Author %s is not creator of the post %s";
    private static final String POST_NOT_FOUND = "Post %s is not found";
    private static final String DEFAULT_TIMEZONE = "UTC";

    private final PostDao postDao;
    private final PostMapper postMapper;
    private final FeedDao feedDao;
    private final NewsFeedService newsFeedService;

    @Override
    public UUID post(UUID uuid, PostRequest request) {
        var now = LocalDateTime.now(ZoneId.of(DEFAULT_TIMEZONE));
        var post = postMapper.toPost(request)
                .setUuid(UUID.randomUUID())
                .setAuthor(uuid)
                .setCreated(now)
                .setUpdated(now);
        var id = postDao.insert(post);
        newsFeedService.addPost(uuid, post);
        return id;
    }

    @Override
    public void update(UUID uuid, PostUpdateRequest request) {
        var postBox = postDao.findByUUID(request.getId());
        if (postBox.isEmpty()) {
            throw new NotFoundException(POST_NOT_FOUND.formatted(request.getId()));
        }
        var existingPost = postBox.get();
        if (Objects.equals(existingPost.getAuthor(), uuid)) {
            var now = LocalDateTime.now(ZoneId.of(DEFAULT_TIMEZONE));
            existingPost.setUpdated(now);
            existingPost.setText(request.getText());
            postDao.update(existingPost);
            newsFeedService.addPost(uuid, existingPost);
            return;
        }
        throw new IsNotAuthenticated(AUTHOR_NOT_AUTHENTICATED.formatted(uuid, request.getId()));
    }

    @Override
    public void delete(UUID author, UUID post) {
        var postBox = postDao.findByUUID(post);
        if (postBox.isEmpty()) {
            return;
        }
        var existingPost = postBox.get();
        if (Objects.equals(existingPost.getAuthor(), author)) {
            postDao.delete(post);
            newsFeedService.deletePost(author, post);
            return;
        }
        throw new IsNotAuthenticated(AUTHOR_NOT_AUTHENTICATED.formatted(author, post));
    }

    @Override
    public Post get(UUID id) {
        var postBox = postDao.findByUUID(id);
        if (postBox.isEmpty()) {
            throw new NotFoundException(POST_NOT_FOUND.formatted(id));
        }
        return postBox.get();
    }

    @Override
    public List<Post> getFeed(UUID uuid) {
        var feed = newsFeedService.getFeed(uuid);
        var celebrityFeed = feedDao.findFeedForUser(uuid, getSinceDate(feed));
        return merge(feed, celebrityFeed);
    }

    private LocalDateTime getSinceDate(List<Post> feed) {
        if (CollectionUtils.isEmpty(feed)) {
            return LocalDateTime.now(ZoneId.of(DEFAULT_TIMEZONE)).minusWeeks(1);
        }
        return feed.get(0).getCreated();
    }

    private List<Post> merge(List<Post> news, List<Post> celebrityNews) {
        return CollectionUtils.collate(news, celebrityNews, Comparator.comparing(Post::getCreated).reversed());
    }
}
