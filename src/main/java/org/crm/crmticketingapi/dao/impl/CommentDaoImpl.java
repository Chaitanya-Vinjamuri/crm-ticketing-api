package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.CommentDao;
import org.crm.crmticketingapi.entity.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.persist(comment);

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
    }

    @Override
    public Comment findById(
            Long id) {

        Session session =
                sessionFactory.openSession();

        try {

            return session.get(
                    Comment.class,
                    id
            );

        } finally {

            session.close();
        }
    }

    @Override
    public List<Comment> findAll() {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Comment",
                            Comment.class
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }

    @Override
    public void delete(
            Long id) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            Comment comment =
                    session.get(
                            Comment.class,
                            id
                    );

            if (comment != null) {

                session.remove(comment);
            }

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
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

    @Override
    public void update(
            Comment comment) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.merge(comment);

            transaction.commit();

        } catch (Exception ex) {

            if (transaction != null) {

                transaction.rollback();
            }

            throw ex;

        } finally {

            session.close();
        }
    }
}