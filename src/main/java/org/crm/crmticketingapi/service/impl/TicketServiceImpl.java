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
import org.crm.crmticketingapi.util.CodeGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.crm.crmticketingapi.util.ValidationUtil;

import java.sql.Timestamp;
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

        if (request == null) {

            throw new IllegalArgumentException(
                    "Ticket request cannot be null"
            );
        }

        Timestamp now =
                new Timestamp(
                        System.currentTimeMillis()
                );

        Timestamp slaDueAt;

        switch (request.getPriority()) {

            case LOW:
                slaDueAt =
                        new Timestamp(
                                now.getTime()
                                        + (72L * 60 * 60 * 1000)
                        );
                break;

            case MEDIUM:
                slaDueAt =
                        new Timestamp(
                                now.getTime()
                                        + (48L * 60 * 60 * 1000)
                        );
                break;

            case HIGH:
                slaDueAt =
                        new Timestamp(
                                now.getTime()
                                        + (24L * 60 * 60 * 1000)
                        );
                break;

            default:
                slaDueAt = now;
        }

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    request.getAgentId()
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + request.getAgentId()
            );
        }

        // agent validation...

        Ticket ticket =
                Ticket.builder()
                        .ticketCode(
                                CodeGeneratorUtil
                                        .generateTicketCode()
                        )
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .customerEmail(request.getCustomerEmail())
                        .priority(request.getPriority())
                        .issueType(request.getIssueType())
                        .status(TicketStatus.OPEN)
                        .createdAt(now)
                        .slaDueAt(slaDueAt)
                        .assignedAgent(agent)
                        .build();

        try {

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

        } catch (Exception ex) {

            logger.error(
                    "Failed to create ticket for agent id {}",
                    request.getAgentId(),
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Ticket getTicketById(
            Long id) {

        ValidationUtil.validateId(
                id,
                "Ticket"
        );

        logger.info(
                "Fetching ticket with id {}",
                id
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            logger.error(
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

        ValidationUtil.validateId(
                id,
                "Ticket"
        );

        logger.info(
                "Deleting ticket with id {}",
                id
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            logger.error(
                    "Ticket not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + id
            );
        }

        try {

            ticketDao.delete(id);

            logger.info(
                    "Ticket deleted successfully with id {}",
                    id
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to delete ticket with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Ticket updateTicket(
            Long id,
            CreateTicketRequest request) {

        ValidationUtil.validateId(
                id,
                "Ticket"
        );

        if (request == null) {

            throw new IllegalArgumentException(
                    "Ticket request cannot be null"
            );
        }

        logger.info(
                "Updating ticket with id {}",
                id
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            logger.error(
                    "Ticket not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + id
            );
        }

        Agent agent =
                agentDao.findById(
                        request.getAgentId()
                );

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    request.getAgentId()
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + request.getAgentId()
            );
        }

        try {

            ticket.setTitle(
                    request.getTitle()
            );

            ticket.setDescription(
                    request.getDescription()
            );

            ticket.setCustomerEmail(
                    request.getCustomerEmail()
            );

            ticket.setPriority(
                    request.getPriority()
            );

            ticket.setIssueType(
                    request.getIssueType()
            );

            ticket.setAssignedAgent(
                    agent
            );

            ticketDao.update(ticket);

            logger.info(
                    "Ticket updated successfully with id {}",
                    id
            );

            return ticket;

        } catch (Exception ex) {

            logger.error(
                    "Failed to update ticket with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }
}