package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
import org.crm.crmticketingapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
@Transactional
public class TicketServiceImpl
        implements TicketService {

    private final TicketDao ticketDao;

    private final AgentDao agentDao;

    @Override
    public Ticket createTicket(
            CreateTicketRequest request) {

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (agent == null) {

            throw new RuntimeException(
                    "Agent not found with id : "
                            + request.getAgentId()
            );
        }

        Ticket ticket =
                Ticket.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .customerEmail(request.getCustomerEmail())
                        .priority(request.getPriority())
                        .issueType(request.getIssueType())
                        .status(TicketStatus.OPEN)
                        .createdAt(LocalDateTime.now())
                        .assignedAgent(agent)
                        .build();

        ticketDao.save(ticket);

        agent.setAssignedTicketCount(
                agent.getAssignedTicketCount() + 1
        );

        agentDao.update(agent);

        return ticket;
    }

    @Override
    public Ticket getTicketById(
            Long id) {

        return ticketDao.findById(id);
    }

    @Override
    public List<Ticket> getAllTickets() {

        return ticketDao.findAll();
    }

    @Override
    public void deleteTicket(
            Long id) {

        ticketDao.delete(id);
    }
}