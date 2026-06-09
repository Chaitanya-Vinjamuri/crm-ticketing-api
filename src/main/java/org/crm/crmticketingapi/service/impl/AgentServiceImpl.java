package org.crm.crmticketingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.AgentDao;
import org.crm.crmticketingapi.dto.request.CreateAgentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.service.AgentService;
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
public class AgentServiceImpl
        implements AgentService {

    private final AgentDao agentDao;

    @Override
    public Agent createAgent(
            CreateAgentRequest request) {

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

        return agent;
    }

    @Override
    public Agent getAgentById(
            Long id) {

        return agentDao.findById(id);
    }

    @Override
    public List<Agent> getAllAgents() {

        return agentDao.findAll();
    }

    @Override
    public void deleteAgent(
            Long id) {

        agentDao.delete(id);
    }
}