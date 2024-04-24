package ru.otus.otusx.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.logic.dto.request.PostRequest;
import ru.otus.otusx.logic.dto.request.PostUpdateRequest;
import ru.otus.otusx.logic.facade.PostService;
import ru.otus.otusx.logic.service.UserSecurityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserSecurityService userSecurityService;

    @PostMapping("/create")
    public UUID create(@RequestBody PostRequest post) {
        return postService.post(userSecurityService.getUuid(), post);
    }

    @PutMapping("/update")
    public void update(@RequestBody PostUpdateRequest post) {
        postService.update(userSecurityService.getUuid(), post);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        postService.delete(userSecurityService.getUuid(), id);
    }

    @GetMapping("/get/{id}")
    public Post get(@PathVariable UUID id) {
        return postService.get(id);
    }

    @GetMapping("/feed")
    public List<Post> feed() {
        return postService.getFeed(userSecurityService.getUuid());
    }
}
