package ru.otus.otusx.dao;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;
import ru.otus.otusx.dao.entity.Post;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FeedCacheDao {

    private static final int INVALIDATE_LIMIT = 1000;

    @Resource(name = "redisTemplate")
    //UserUUID, PostUUID, Post
    private HashOperations<String, String, Post> hashOperations;

    private void add(UUID userId, UUID postId, Post post) {
        hashOperations.put(userId.toString(), postId.toString(), post);
    }

    public void add(Post post, List<UUID> followers) {
        for (var follower : followers) {
            add(follower, post.getUuid(), post);
        }
    }

    public void delete(Post post, List<UUID> followers) {
        for (var follower : followers) {
            delete(follower, post.getUuid());
        }
    }

    private void delete(UUID follower, UUID uuid) {
        hashOperations.delete(follower.toString(), uuid.toString());
    }

    public List<Post> getFeed(UUID user) {
        return hashOperations.values(user.toString());
    }

    public void invalidate(UUID uuid) {
        if (hashOperations.size(uuid.toString()) > INVALIDATE_LIMIT) {
            var posts = hashOperations.values(uuid.toString());
            int countToRemove = posts.size() - INVALIDATE_LIMIT;
            Object[] uuids = new Object[countToRemove];
            for (int i = 0; i < countToRemove; i++) {
                uuids[i] = posts.get(i).getUuid().toString();
            }
            hashOperations.delete(uuid.toString(), uuids);
        }
    }
}
