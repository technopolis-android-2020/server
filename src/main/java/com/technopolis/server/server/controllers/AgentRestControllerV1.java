package com.technopolis.server.server.controllers;

import com.technopolis.server.database.model.Agent;
import com.technopolis.server.database.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/agent/")
public class AgentRestControllerV1 {

    private final AgentService agentService;

    @Autowired
    public AgentRestControllerV1(@NotNull final AgentService agentService){
        this.agentService = agentService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Object>> getAllAgents() {
        List<Object> response = getResponse(agentService.findAll());

        return ResponseEntity.ok(response);
    }

    private List<Object> getResponse(@NotNull final List<Agent> agents) {
        List<Object> response = new LinkedList<>();
        for (Agent agent : agents) {
            Map<Object, Object> oneAgent = new HashMap<>();
            oneAgent.put("id", agent.getId());
            oneAgent.put("title", agent.getName());
            oneAgent.put("imageUrl", agent.getPreviewImageUrl());
            // в будущем надо решить как отправлять комментарии.
            // с новостью или отдельно
            response.add(oneAgent);
        }
        return response;
    }

}
