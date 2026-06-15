package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.dao.base.GenericDao;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;

import java.util.List;

public interface TicketDao
        extends GenericDao<Ticket> {

    List<Ticket> findByStatus(
            TicketStatus status
    );
}