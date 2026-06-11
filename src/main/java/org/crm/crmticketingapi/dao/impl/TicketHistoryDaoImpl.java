package org.crm.crmticketingapi.dao.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.TicketHistoryDao;
import org.crm.crmticketingapi.entity.TicketHistory;
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
public class TicketHistoryDaoImpl
        implements TicketHistoryDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(
            TicketHistory ticketHistory) {

        Session session =
                sessionFactory.openSession();

        Transaction transaction =
                null;

        try {

            transaction =
                    session.beginTransaction();

            session.persist(
                    ticketHistory
            );

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
    public List<TicketHistory> findAll() {

        Session session =
                sessionFactory.openSession();

        try {

            return session
                    .createQuery(
                            "FROM TicketHistory",
                            TicketHistory.class
                    )
                    .getResultList();

        } finally {

            session.close();
        }
    }
}