package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dto.request.CreateAgentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.exception.ResourceNotFoundException;
import org.crm.crmticketingapi.service.AgentService;
import org.crm.crmticketingapi.util.CodeGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.crm.crmticketingapi.util.ValidationUtil;
import java.sql.Timestamp;
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

        if (request == null) {

            throw new IllegalArgumentException(
                    "Agent request cannot be null"
            );
        }

        logger.info(
                "Creating agent with email {}",
                request.getEmail()
        );

        try {

            Agent agent =
                    Agent.builder()
                            .agentCode(
                                    CodeGeneratorUtil
                                            .generateAgentCode()
                            )
                            .name(request.getName())
                            .email(request.getEmail())
                            .department(request.getDepartment())
                            .active(request.getActive())
                            .assignedTicketCount(0)
                            .createdAt(
                                    new Timestamp(
                                            System.currentTimeMillis()
                                    )
                            )
                            .build();

            agentDao.save(agent);

            logger.info(
                    "Agent created successfully with id {}",
                    agent.getId()
            );

            return agent;

        } catch (Exception ex) {

            logger.error(
                    "Failed to create agent with email {}",
                    request.getEmail(),
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Agent getAgentById(
            Long id) {
        ValidationUtil.validateId(
                id,
                "Agent"
        );

        logger.info(
                "Fetching agent with id {}",
                id
        );

        Agent agent =
                agentDao.findById(id);

        if (agent == null) {
            logger.error(
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

        ValidationUtil.validateId(
                id,
                "Agent"
        );

        logger.info(
                "Deleting agent with id {}",
                id
        );

        Agent agent =
                agentDao.findById(id);

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : " + id
            );
        }

        try {

            agentDao.delete(id);

            logger.info(
                    "Agent deleted successfully with id {}",
                    id
            );

        } catch (Exception ex) {

            logger.error(
                    "Failed to delete agent with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }

    @Override
    public Agent updateAgent(
            Long id,
            CreateAgentRequest request) {

        ValidationUtil.validateId(
                id,
                "Agent"
        );

        if (request == null) {

            throw new IllegalArgumentException(
                    "Agent request cannot be null"
            );
        }

        logger.info(
                "Updating agent with id {}",
                id
        );

        Agent agent =
                agentDao.findById(id);

        if (agent == null) {

            logger.error(
                    "Agent not found with id {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Agent not found with id : " + id
            );
        }

        try {

            agent.setName(request.getName());
            agent.setEmail(request.getEmail());
            agent.setDepartment(request.getDepartment());
            agent.setActive(request.getActive());

            agentDao.update(agent);

            logger.info(
                    "Agent updated successfully with id {}",
                    id
            );

            return agent;

        } catch (Exception ex) {

            logger.error(
                    "Failed to update agent with id {}",
                    id,
                    ex
            );

            throw ex;
        }
    }
}