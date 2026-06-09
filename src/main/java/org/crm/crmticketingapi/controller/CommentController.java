package org.crm.crmticketingapi.controller;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.request.CreateCommentRequest;
import org.crm.crmticketingapi.entity.Comment;
import org.crm.crmticketingapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Comment createComment(
            @Valid
            @RequestBody
            CreateCommentRequest request) {

        return commentService.createComment(
                request
        );
    }

    @GetMapping("/{id}")
    public Comment getCommentById(
            @PathVariable
            Long id) {

        return commentService.getCommentById(
                id
        );
    }

    @GetMapping
    public List<Comment> getAllComments() {

        return commentService.getAllComments();
    }

    @DeleteMapping("/{id}")
    public void deleteComment(
            @PathVariable
            Long id) {

        commentService.deleteComment(id);
    }
}