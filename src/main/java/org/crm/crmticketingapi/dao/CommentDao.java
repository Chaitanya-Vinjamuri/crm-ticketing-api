package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.entity.Comment;

import java.util.List;

public interface CommentDao {

    void save(Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    void delete(Long id);

    List<Comment> findByTicketId(
            Long ticketId
    );
}