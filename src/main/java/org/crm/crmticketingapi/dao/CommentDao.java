package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.dao.base.GenericDao;
import org.crm.crmticketingapi.entity.Comment;

import java.util.List;

public interface CommentDao
        extends GenericDao<Comment> {

    List<Comment> findByTicketId(
            Long ticketId
    );
}