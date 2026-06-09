package org.crm.crmticketingapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @Valid
            @RequestBody
            CreateTicketRequest request) {

        Ticket ticket =
                ticketService.createTicket(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(
            @PathVariable
            Long id) {

        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {

        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable
            Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                "Ticket deleted successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable
            Long id,

            @Valid
            @RequestBody
            CreateTicketRequest request) {

        return ResponseEntity.ok(
                ticketService.updateTicket(
                        id,
                        request
                )
        );
    }
}