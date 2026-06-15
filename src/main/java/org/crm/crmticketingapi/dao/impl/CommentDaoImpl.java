package org.crm.crmticketingapi.dao.impl;

import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.dao.base.GenericDaoImpl;
import org.crm.crmticketingapi.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl
        extends GenericDaoImpl<Comment>
        implements CommentDao {

    private final SessionFactory sessionFactory;

    public CommentDaoImpl(
            SessionFactory sessionFactory) {

        super(
                sessionFactory,
                Comment.class
        );

        this.sessionFactory =
                sessionFactory;
    }

    @Override
    public List<Comment> findByTicketId(
            Long ticketId) {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Comment WHERE ticket.id = :ticketId",
                            Comment.class
                    )
                    .setParameter(
                            "ticketId",
                            ticketId
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }
}