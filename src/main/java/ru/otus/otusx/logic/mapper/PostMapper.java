package ru.otus.otusx.logic.mapper;

import org.springframework.stereotype.Component;
import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.logic.dto.request.PostRequest;
import ru.otus.otusx.logic.dto.request.PostUpdateRequest;

@Component
public class PostMapper {


    public Post toPost(PostRequest post) {
        return new Post()
                .setText(post.getText());
    }

    public Post toPost(PostUpdateRequest post) {
        return new Post()
                .setUuid(post.getId())
                .setText(post.getText());
    }
}
