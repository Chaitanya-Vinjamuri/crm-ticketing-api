package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dto.request.CreateAgentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
@Transactional
public class AgentServiceImpl
        implements AgentService {

    private final AgentDao agentDao;

    private static final Logger logger =
            LoggerFactory.getLogger(
                    AgentServiceImpl.class
            );



    @Override
    public Agent createAgent(
            CreateAgentRequest request) {

        logger.info(
                "Creating agent with email {}",
                request.getEmail()
        );

        Agent agent =
                Agent.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .department(request.getDepartment())
                        .active(request.getActive())
                        .assignedTicketCount(0)
                        .createdAt(LocalDateTime.now())
                        .build();

        agentDao.save(agent);
        logger.info(
                "Agent created successfully with id {}",
                agent.getId()
        );

        return agent;
    }

    @Override
    public Agent getAgentById(
            Long id) {
        logger.info(
                "Fetching agent with id {}",
                id
        );

        Agent agent =
                agentDao.findById(id);

        if (agent == null) {
            logger.warn(
                    "Agent not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : " + id
            );
        }
        return agent;
    }

    @Override
    public List<Agent> getAllAgents() {

        return agentDao.findAll();
    }

    @Override
    public void deleteAgent(
            Long id) {

        logger.info(
                "Deleting agent with id {}",
                id
        );

        Agent ag = agentDao.findById(id);
        if (ag == null) {

            logger.warn(
                    "Agent not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : " + id
            );
        }

        agentDao.delete(id);
        logger.info(
                "Agent deleted successfully with id {}",
                id
        );
    }

    @Override
    public Agent updateAgent(
            Long id,
            CreateAgentRequest request) {

        logger.info(
                "Updating agent with id {}",
                id
        );

        Agent agent =
                agentDao.findById(id);

        if (agent == null) {

            logger.warn(
                    "Agent not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : " + id
            );
        }

        agent.setName(
                request.getName()
        );

        agent.setEmail(
                request.getEmail()
        );

        agent.setDepartment(
                request.getDepartment()
        );

        agent.setActive(
                request.getActive()
        );

        agentDao.update(agent);

        logger.info(
                "Agent updated successfully with id {}",
                id
        );

        return agent;
    }
}