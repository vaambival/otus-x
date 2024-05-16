package ru.otus.otusx.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.otusx.dao.entity.Post;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NewsfeedMessage {
    private List<UUID> followers;
    private Post post;
    private boolean delete;
}
