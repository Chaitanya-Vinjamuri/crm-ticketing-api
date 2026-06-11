package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.entity.TicketHistory;

import java.util.List;

public interface TicketHistoryDao {

    void save(
            TicketHistory ticketHistory
    );

    List<TicketHistory> findAll();
}