package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class CommentDaoImpl
        implements CommentDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(
            Comment comment) {

        sessionFactory
                .getCurrentSession()
                .persist(comment);
    }

    @Override
    public Comment findById(
            Long id) {

        return sessionFactory
                .getCurrentSession()
                .get(
                        Comment.class,
                        id
                );
    }

    @Override
    public List<Comment> findAll() {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Comment",
                        Comment.class
                )
                .getResultList();
    }

    @Override
    public void delete(
            Long id) {

        Session session =
                sessionFactory
                        .getCurrentSession();

        Comment comment =
                session.get(
                        Comment.class,
                        id
                );

        if (comment != null) {

            session.remove(comment);
        }
    }

    @Override
    public List<Comment> findByTicketId(
            Long ticketId) {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Comment WHERE ticket.id = :ticketId",
                        Comment.class
                )
                .setParameter(
                        "ticketId",
                        ticketId
                )
                .getResultList();
    }
}