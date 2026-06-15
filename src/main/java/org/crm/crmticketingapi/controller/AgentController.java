package org.crm.crmticketingapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.entity.Agent;
import org.crm.crmticketingapi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor(
        onConstructor = @__(@Autowired)
)
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Agent> createAgent(
            @Valid
            @RequestBody
            Agent agent) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        agentService.createAgent(agent)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                agentService.getAgentById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {

        return ResponseEntity.ok(
                agentService.getAllAgents()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgent(
            @PathVariable Long id) {

        agentService.deleteAgent(id);

        return ResponseEntity.ok(
                "Agent deleted successfully"
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Agent> updateAgent(
            @PathVariable Long id,
            @Valid
            @RequestBody
            Agent agent) {

        return ResponseEntity.ok(
                agentService.updateAgent(
                        id,
                        agent
                )
        );
    }
}