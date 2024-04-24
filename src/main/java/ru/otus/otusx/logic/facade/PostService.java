package ru.otus.otusx.logic.facade;

import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.logic.dto.request.PostRequest;
import ru.otus.otusx.logic.dto.request.PostUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface PostService {
    UUID post(UUID uuid, PostRequest post);

    void update(UUID uuid, PostUpdateRequest post);

    void delete(UUID author, UUID post);

    Post get(UUID id);

    List<Post> getFeed(UUID uuid);
}
