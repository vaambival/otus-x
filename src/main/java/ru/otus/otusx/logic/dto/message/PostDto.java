package ru.otus.otusx.logic.dto.message;

import lombok.Getter;
import lombok.Setter;
import ru.otus.otusx.dao.entity.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostDto {

    private UUID postId;
    private String postText;
    private UUID userId;
    private List<UUID> subscribers;

    public PostDto(Post post, Collection<UUID> subscribers) {
        this.postId = post.getUuid();
        this.postText = post.getText();
        this.userId = post.getAuthor();
        this.subscribers = new ArrayList<>(subscribers);
    }
}
