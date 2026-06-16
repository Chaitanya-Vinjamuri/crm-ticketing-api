package org.crm.crmticketingapi.service.impl;
import org.crm.crmticketingapi.dto.event.HistoryEvent;
import org.crm.crmticketingapi.kafka.producer.HistoryEventProducer;
import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.cache.LruCache;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dao.TicketDao;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.entity.Ticket;
import org.crm.crmticketingapi.enums.TicketStatus;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.TicketService;
import org.crm.crmticketingapi.util.CodeGeneratorUtil;
import org.crm.crmticketingapi.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final LruCache<Long, Ticket> lruCache;

    private final HistoryEventProducer historyEventProducer;

    private static final Logger logger =
            LoggerFactory.getLogger(
                    TicketServiceImpl.class
            );

    @Override
    public Ticket updateStatus(
            Long id,
            TicketStatus status) {

        ValidationUtil.validateId(
                id,
                "Ticket"
        );

        Ticket ticket =
                ticketDao.findById(id);

        if (ticket == null) {

            throw new ResourceNotFoundException(
                    "Ticket not found with id : "
                            + id
            );
        }

        ticket.setStatus(
                status
        );

        if (status == TicketStatus.RESOLVED) {

            ticket.setResolvedAt(
                    new Timestamp(
                            System.currentTimeMillis()
                    )
            );
        }

        ticketDao.update(
                ticket
        );

        lruCache.put(
                id,
                ticket
        );

        historyEventProducer.publish(
                HistoryEvent.builder()
                        .objectType("TICKET")
                        .objectId(ticket.getId())
                        .action(
                                status.name()
                        )
                        .build()
        );

        logger.info(
                "Ticket status updated successfully for id {}",
                id
        );

        return ticket;
    }

    @Override
    public Ticket createTicket(
            Ticket ticket) {

        if (ticket == null) {

            throw new IllegalArgumentException(
                    "Ticket request cannot be null"
            );
        }

        Timestamp now =
                new Timestamp(
                        System.currentTimeMillis()
                );

        Timestamp slaDueAt;

        switch (ticket.getPriority()) {

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
                        ticket.getAgentId()
                );

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    ticket.getAgentId()
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : "
                            + ticket.getAgentId()
            );
        }

        ticket.setTicketCode(
                CodeGeneratorUtil.generateTicketCode()
        );

        ticket.setStatus(
                TicketStatus.OPEN
        );

        ticket.setCreatedAt(
                now
        );

        ticket.setSlaDueAt(
                slaDueAt
        );

        ticket.setAssignedAgent(
                agent
        );

        try {

            logger.info(
                    "Creating ticket for agent id {}",
                    ticket.getAgentId()
            );

            ticketDao.save(
                    ticket
            );

            historyEventProducer.publish(
                    HistoryEvent.builder()
                            .objectType("TICKET")
                            .objectId(ticket.getId())
                            .action("CREATE")
                            .build()
            );

            lruCache.put(
                    ticket.getId(),
                    ticket
            );

            agent.setAssignedTicketCount(
                    agent.getAssignedTicketCount() + 1
            );

            agentDao.update(
                    agent
            );

            logger.info(
                    "Ticket created successfully with id {}",
                    ticket.getId()
            );

            return ticket;

        } catch (Exception ex) {

            logger.error(
                    "Failed to create ticket for agent id {}",
                    ticket.getAgentId(),
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
                lruCache.get(id);

        if (ticket != null) {

            logger.info(
                    "Ticket fetched from LRU cache"
            );

            return ticket;
        }

        ticket =
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

        lruCache.put(
                id,
                ticket
        );

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

            historyEventProducer.publish(
                    HistoryEvent.builder()
                            .objectType("TICKET")
                            .objectId(id)
                            .action("DELETE")
                            .build()
            );

            lruCache.remove(id);

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
            Ticket request) {

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
                ticketDao.findById(
                        id
                );

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

            ticketDao.update(
                    ticket
            );

            historyEventProducer.publish(
                    HistoryEvent.builder()
                            .objectType("TICKET")
                            .objectId(ticket.getId())
                            .action("UPDATE")
                            .build()
            );

            lruCache.put(
                    id,
                    ticket
            );

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