package org.crm.crmticketingapi.service;

import org.crm.crmticketingapi.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(
            Comment comment
    );

    Comment getCommentById(
            Long id
    );

    List<Comment> getAllComments();

    void deleteComment(
            Long id
    );

    Comment updateComment(
            Long id,
            Comment comment
    );
}