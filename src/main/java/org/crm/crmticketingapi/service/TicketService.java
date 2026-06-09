package org.crm.crmticketingapi.service;

import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket createTicket(
            CreateTicketRequest request
    );

    Ticket getTicketById(
            Long id
    );

    List<Ticket> getAllTickets();

    void deleteTicket(
            Long id
    );

    Ticket updateTicket(
            Long id,
            CreateTicketRequest request
    );
}