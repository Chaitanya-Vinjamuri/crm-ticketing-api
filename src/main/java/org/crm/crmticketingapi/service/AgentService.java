package org.crm.crmticketingapi.service;

import org.crm.crmticketingapi.dto.request.CreateAgentRequest;
import org.crm.crmticketingapi.entity.Agent;

import java.util.List;

public interface AgentService {

    Agent createAgent(
            CreateAgentRequest request
    );

    Agent getAgentById(
            Long id
    );

    List<Agent> getAllAgents();

    void deleteAgent(
            Long id
    );
}