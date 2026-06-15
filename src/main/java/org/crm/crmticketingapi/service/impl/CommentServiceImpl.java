package org.crm.crmticketingapi.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Comment;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.CommentService;
import org.crm.crmticketingapi.util.CodeGeneratorUtil;
import org.crm.crmticketingapi.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final Cache<Long, Comment> commentCache;

    private static final Logger logger =
            LoggerFactory.getLogger(
                    CommentServiceImpl.class
            );

    @Override
    public Comment createComment(
            Comment comment) {

        if (comment == null) {

            throw new IllegalArgumentException(
                    "Comment request cannot be null"
            );
        }

        logger.info(
                "Creating comment for ticket id {} by agent id {}",
                comment.getTicket().getId(),
                comment.getAgent().getId()
        );

        Ticket ticket =
                ticketDao.findById(
                        comment.getTicket().getId()
                );

        Agent agent =
                agentDao.findById(
                        comment.getAgent().getId()
                );

        if (ticket == null) {

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + comment.getTicket().getId()
            );
        }

        if (agent == null) {

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + comment.getAgent().getId()
            );
        }

        comment.setCommentCode(
                CodeGeneratorUtil.generateCommentCode()
        );

        comment.setCreatedAt(
                new Timestamp(
                        System.currentTimeMillis()
                )
        );

        comment.setTicket(
                ticket
        );

        comment.setAgent(
                agent
        );

        commentDao.save(
                comment
        );

        commentCache.put(
                comment.getId(),
                comment
        );

        logger.info(
                "Comment created successfully with id {}",
                comment.getId()
        );

        return comment;
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
                commentCache.getIfPresent(
                        id
                );

        if (comment != null) {

            logger.info(
                    "Comment fetched from Caffeine cache"
            );

            return comment;
        }

        comment =
                commentDao.findById(
                        id
                );



        if (comment == null) {

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        commentCache.put(
                id,
                comment
        );
        logger.info(
                "Comment stored in Caffeine cache"
        );

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

        Comment comment =
                commentDao.findById(
                        id
                );

        if (comment == null) {

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        commentDao.delete(
                id
        );

        commentCache.invalidate(
                id
        );

        logger.info(
                "Comment deleted successfully with id {}",
                id
        );
    }

    @Override
    public Comment updateComment(
            Long id,
            Comment updatedComment) {

        ValidationUtil.validateId(
                id,
                "Comment"
        );

        if (updatedComment == null) {

            throw new IllegalArgumentException(
                    "Comment request cannot be null"
            );
        }

        Comment comment =
                commentDao.findById(
                        id
                );

        if (comment == null) {

            throw new ResourceNotFoundException(
                    "Comment not found with id : "
                            + id
            );
        }

        Ticket ticket =
                ticketDao.findById(
                        updatedComment.getTicket().getId()
                );

        Agent agent =
                agentDao.findById(
                        updatedComment.getAgent().getId()
                );

        if (ticket == null) {

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + updatedComment.getTicket().getId()
            );
        }

        if (agent == null) {

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + updatedComment.getAgent().getId()
            );
        }

        comment.setMessage(
                updatedComment.getMessage()
        );

        comment.setTicket(
                ticket
        );

        comment.setAgent(
                agent
        );

        commentDao.update(
                comment
        );

        commentCache.put(
                id,
                comment
        );

        logger.info(
                "Comment updated successfully with id {}",
                id
        );

        return comment;
    }
}