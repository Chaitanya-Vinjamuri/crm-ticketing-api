package org.crm.crmticketingapi.dao.impl;

import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.dao.base.GenericDaoImpl;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDaoImpl
        extends GenericDaoImpl<Ticket>
        implements TicketDao {

    private final SessionFactory sessionFactory;

    public TicketDaoImpl(
            SessionFactory sessionFactory) {

        super(
                sessionFactory,
                Ticket.class
        );

        this.sessionFactory =
                sessionFactory;
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