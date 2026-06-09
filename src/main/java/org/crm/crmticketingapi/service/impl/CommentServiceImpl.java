package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.dto.request.CreateCommentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Comment;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger =
            LoggerFactory.getLogger(
                    CommentServiceImpl.class
            );

    @Override
    public Comment createComment(
            CreateCommentRequest request) {

        logger.info(
                "Creating comment for ticket id {} by agent id {}",
                request.getTicketId(),
                request.getAgentId()
        );

        Ticket ticket =
                ticketDao.findById(
                        request.getTicketId()
                );

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (ticket == null) {

            logger.warn(
                    "Ticket not found with id {}",
                    request.getTicketId()
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + request.getTicketId()
            );
        }

        if (agent == null) {

            logger.warn(
                    "Agent not found with id {}",
                    request.getAgentId()
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + request.getAgentId()
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

        logger.info(
                "Comment created successfully with id {}",
                comment.getId()
        );

        return comment;
    }

    @Override
    public Comment getCommentById(
            Long id) {

        logger.info(
                "Fetching comment with id {}",
                id
        );

        Comment comment =
                commentDao.findById(id);

        if (comment == null) {

            logger.warn(
                    "Comment not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        return comment;
    }

    @Override
    public List<Comment> getAllComments() {

        logger.info(
                "Fetching all comments"
        );

        return commentDao.findAll();
    }

    @Override
    public void deleteComment(
            Long id) {

        logger.info(
                "Deleting comment with id {}",
                id
        );

        Comment comment =
                commentDao.findById(id);

        if (comment == null) {

            logger.warn(
                    "Comment not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        commentDao.delete(id);

        logger.info(
                "Comment deleted successfully with id {}",
                id
        );
    }
}