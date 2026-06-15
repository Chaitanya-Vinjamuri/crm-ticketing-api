package org.crm.crmticketingapi.dao.impl;

import org.crm.crmticketingapi.dao.TicketHistoryDao;
import org.crm.crmticketingapi.dao.base.GenericDaoImpl;
import org.crm.crmticketingapi.entity.TicketHistory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TicketHistoryDaoImpl
        extends GenericDaoImpl<TicketHistory>
        implements TicketHistoryDao {

    public TicketHistoryDaoImpl(
            SessionFactory sessionFactory) {

        super(
                sessionFactory,
                TicketHistory.class
        );
    }
}