package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

        sessionFactory
                .getCurrentSession()
                .persist(ticket);
    }

    @Override
    public Ticket findById(
            Long id) {

        return sessionFactory
                .getCurrentSession()
                .get(
                        Ticket.class,
                        id
                );
    }

    @Override
    public List<Ticket> findAll() {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Ticket",
                        Ticket.class
                )
                .getResultList();
    }

    @Override
    public void update(
            Ticket ticket) {

        sessionFactory
                .getCurrentSession()
                .merge(ticket);
    }

    @Override
    public void delete(
            Long id) {

        Session session =
                sessionFactory
                        .getCurrentSession();

        Ticket ticket =
                session.get(
                        Ticket.class,
                        id
                );

        if (ticket != null) {

            session.remove(ticket);
        }
    }

    @Override
    public List<Ticket> findByStatus(
            TicketStatus status) {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM Ticket WHERE status = :status",
                        Ticket.class
                )
                .setParameter(
                        "status",
                        status
                )
                .getResultList();
    }
}