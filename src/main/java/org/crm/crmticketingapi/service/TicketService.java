package org.crm.crmticketingapi.service;

import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    Ticket updateStatus(
            Long id,
            TicketStatus status
    );

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