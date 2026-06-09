package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.dto.request.CreateTicketRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger =
            LoggerFactory.getLogger(
                    TicketServiceImpl.class
            );

    @Override
    public Ticket createTicket(
            CreateTicketRequest request) {

        logger.info(
                "Creating ticket for agent id {}",
                request.getAgentId()
        );

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (agent == null) {

            logger.warn(
                    "Agent not found with id {}",
                    request.getAgentId()
            );

            throw new ResourceNotFoundException(
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

        logger.info(
                "Ticket created successfully with id {}",
                ticket.getId()
        );

        return ticket;
    }

    @Override
    public Ticket getTicketById(
            Long id) {

        logger.info(
                "Fetching ticket with id {}",
                id
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            logger.warn(
                    "Ticket not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + id
            );
        }

        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {

        logger.info(
                "Fetching all tickets"
        );

        return ticketDao.findAll();
    }

    @Override
    public void deleteTicket(
            Long id) {

        logger.info(
                "Deleting ticket with id {}",
                id
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            logger.warn(
                    "Ticket not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + id
            );
        }

        ticketDao.delete(id);

        logger.info(
                "Ticket deleted successfully with id {}",
                id
        );
    }
}