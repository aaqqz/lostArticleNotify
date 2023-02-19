package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.request.CommentCreate;
import project.toy.api.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public void write(@RequestBody @Valid CommentCreate commentCreate) {
        commentService.write(commentCreate);
    }
}
