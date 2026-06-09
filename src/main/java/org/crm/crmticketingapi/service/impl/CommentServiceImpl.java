package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.dto.request.CreateCommentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Comment;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
@Transactional
public class CommentServiceImpl
        implements CommentService {

    private final CommentDao commentDao;

    private final TicketDao ticketDao;

    private final AgentDao agentDao;

    @Override
    public Comment createComment(CreateCommentRequest request) {

        Ticket ticket = ticketDao.findById(request.getTicketId());

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (ticket == null) {

            throw new RuntimeException(
                    "Ticket not found"
            );
        }

        if (agent == null) {

            throw new RuntimeException(
                    "Agent not found"
            );
        }

        Comment comment =
                Comment.builder()
                        .message(request.getMessage())
                        .createdAt(LocalDateTime.now())
                        .ticket(ticket)
                        .agent(agent)
                        .build();

        commentDao.save(comment);

        return comment;
    }

    @Override
    public Comment getCommentById(
            Long id) {

        return commentDao.findById(id);
    }

    @Override
    public List<Comment> getAllComments() {

        return commentDao.findAll();
    }

    @Override
    public void deleteComment(
            Long id) {

        commentDao.delete(id);
    }
}