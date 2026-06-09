package org.crm.crmticketingapi.controller;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.request.CreateAgentRequest;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public Agent createAgent(
            @Valid
            @RequestBody
            CreateAgentRequest request) {

        return agentService.createAgent(
                request
        );
    }

    @GetMapping("/{id}")
    public Agent getAgentById(
            @PathVariable
            Long id) {

        return agentService.getAgentById(
                id
        );
    }

    @GetMapping
    public List<Agent> getAllAgents() {

        return agentService.getAllAgents();
    }

    @DeleteMapping("/{id}")
    public void deleteAgent(
            @PathVariable
            Long id) {

        agentService.deleteAgent(id);
    }
}