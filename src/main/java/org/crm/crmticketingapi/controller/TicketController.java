package org.crm.crmticketingapi.controller;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public Ticket createTicket(
            @Valid
            @RequestBody
            CreateTicketRequest request) {

        return ticketService.createTicket(
                request
        );
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(
            @PathVariable
            Long id) {

        return ticketService.getTicketById(
                id
        );
    }

    @GetMapping
    public List<Ticket> getAllTickets() {

        return ticketService.getAllTickets();
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(
            @PathVariable
            Long id) {

        ticketService.deleteTicket(id);
    }
}