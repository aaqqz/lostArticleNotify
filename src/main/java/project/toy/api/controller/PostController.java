package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.toy.api.request.PostCreate;
import project.toy.api.request.PostEdit;
import project.toy.api.request.PostSearch;
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

    @GetMapping("/post")
    public Page<PostResponse> getList(PostSearch postSearch, Pageable pageable) {
        return postService.search(postSearch, pageable);
    }

    @PatchMapping("/post/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {

        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
