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
import org.crm.crmticketingapi.util.CodeGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.crm.crmticketingapi.util.ValidationUtil;

import java.sql.Timestamp;
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

        if (request == null) {

            throw new IllegalArgumentException(
                    "Comment request cannot be null"
            );
        }

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

            logger.error(
                    "Ticket not found with id {}",
                    request.getTicketId()
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + request.getTicketId()
            );
        }

        if (agent == null) {

            logger.error(
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
                        .commentCode(
                                CodeGeneratorUtil
                                        .generateCommentCode()
                        )
                        .message(request.getMessage())
                        .createdAt(
                                new Timestamp(
                                        System.currentTimeMillis()
                                )
                        )
                        .ticket(ticket)
                        .agent(agent)
                        .build();

        try {

            commentDao.save(comment);

            logger.info(
                    "Comment created successfully with id {}",
                    comment.getId()
            );

            return comment;

        } catch (Exception ex) {

            logger.error(
                    "Failed to create comment for ticket id {}",
                    request.getTicketId(),
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Comment getCommentById(
            Long id) {

        ValidationUtil.validateId(
                id,
                "Comment"
        );

        logger.info(
                "Fetching comment with id {}",
                id
        );

        Comment comment =
                commentDao.findById(id);

        if (comment == null) {

            logger.error(
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

        ValidationUtil.validateId(
                id,
                "Comment"
        );

        logger.info(
                "Deleting comment with id {}",
                id
        );

        Comment comment =
                commentDao.findById(id);

        if (comment == null) {

            logger.error(
                    "Comment not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        try {

            commentDao.delete(id);

            logger.info(
                    "Comment deleted successfully with id {}",
                    id
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to delete comment with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Comment updateComment(
            Long id,
            CreateCommentRequest request) {

        ValidationUtil.validateId(
                id,
                "Comment"
        );

        if (request == null) {

            throw new IllegalArgumentException(
                    "Comment request cannot be null"
            );
        }

        logger.info(
                "Updating comment with id {}",
                id
        );

        Comment comment =
                commentDao.findById(id);

        if (comment == null) {

            logger.error(
                    "Comment not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        Ticket ticket =
                ticketDao.findById(
                        request.getTicketId()
                );

        if (ticket == null) {

            logger.error(
                    "Ticket not found with id {}",
                    request.getTicketId()
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + request.getTicketId()
            );
        }

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    request.getAgentId()
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + request.getAgentId()
            );
        }

        try {

            comment.setMessage(
                    request.getMessage()
            );

            comment.setTicket(
                    ticket
            );

            comment.setAgent(
                    agent
            );

            commentDao.update(comment);

            logger.info(
                    "Comment updated successfully with id {}",
                    id
            );

            return comment;

        } catch (Exception ex) {

            logger.error(
                    "Failed to update comment with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }
}