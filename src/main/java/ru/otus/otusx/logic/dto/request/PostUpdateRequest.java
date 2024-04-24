package ru.otus.otusx.logic.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostUpdateRequest {
    private String text;
    private UUID id;
}
