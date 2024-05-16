package ru.otus.otusx.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class Post implements Serializable {

    private UUID uuid;
    private String text;
    private UUID author;
    private LocalDateTime created;
    private LocalDateTime updated;
}
