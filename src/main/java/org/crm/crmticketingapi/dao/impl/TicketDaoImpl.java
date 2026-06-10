package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
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
public class TicketDaoImpl
        implements TicketDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(
            Ticket ticket) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.persist(ticket);

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
    public Ticket findById(
            Long id) {

        Session session =
                sessionFactory.openSession();

        try {

            return session.get(
                    Ticket.class,
                    id
            );

        } finally {

            session.close();
        }
    }

    @Override
    public List<Ticket> findAll() {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Ticket",
                            Ticket.class
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }

    @Override
    public void update(
            Ticket ticket) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.merge(ticket);

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
    public void delete(
            Long id) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            Ticket ticket =
                    session.get(
                            Ticket.class,
                            id
                    );

            if (ticket != null) {

                session.remove(ticket);
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
    public List<Ticket> findByStatus(
            TicketStatus status) {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM Ticket WHERE status = :status",
                            Ticket.class
                    )
                    .setParameter(
                            "status",
                            status
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }
}