package ru.otus.otusx.logic.service;

import ru.otus.otusx.dao.entity.Post;

import java.util.List;
import java.util.UUID;

public interface NewsFeedService {
    void addPost(UUID uuid, Post post);

    void deletePost(UUID author, UUID post);

    void deletePost(Post post, List<UUID> followers);

    void addPost(Post post, List<UUID> followers);

    List<Post> getFeed(UUID user);

    void invalidateNewsfeed();
}
