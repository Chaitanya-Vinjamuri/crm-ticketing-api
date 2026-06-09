package org.crm.crmticketingapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.request.CreateCommentRequest;
import org.crm.crmticketingapi.entity.Comment;
import org.crm.crmticketingapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @Valid
            @RequestBody
            CreateCommentRequest request) {

        Comment comment =
                commentService.createComment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(
            @PathVariable
            Long id) {

        return ResponseEntity.ok(
                commentService.getCommentById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {

        return ResponseEntity.ok(
                commentService.getAllComments()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable
            Long id) {

        commentService.deleteComment(id);

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable
            Long id,

            @Valid
            @RequestBody
            CreateCommentRequest request) {

        return ResponseEntity.ok(
                commentService.updateComment(
                        id,
                        request
                )
        );
    }
}