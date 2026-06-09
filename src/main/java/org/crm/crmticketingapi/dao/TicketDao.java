package org.crm.crmticketingapi.dao;

import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;

import java.util.List;

public interface TicketDao {

    void save(Ticket ticket);

    Ticket findById(Long id);

    List<Ticket> findAll();

    void update(Ticket ticket);

    void delete(Long id);

    List<Ticket> findByStatus(
            TicketStatus status
    );
}