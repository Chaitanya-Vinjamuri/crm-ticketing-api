package org.crm.crmticketingapi.service;

import org.crm.crmticketingapi.entity.Agent;

import java.util.List;

public interface AgentService {

    Agent createAgent(
            Agent agent
    );

    Agent getAgentById(
            Long id
    );

    List<Agent> getAllAgents();

    void deleteAgent(
            Long id
    );

    Agent updateAgent(
            Long id,
            Agent agent
    );
}