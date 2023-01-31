package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.toy.api.request.PostCreate;
import project.toy.api.response.PostResponse;
import project.toy.api.service.PostService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public void write(@RequestBody @Valid PostCreate postCreate) {
        postCreate.validate();
        postService.write(postCreate);
    }

    @GetMapping("/post/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }
}
