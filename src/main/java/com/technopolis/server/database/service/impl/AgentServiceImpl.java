package com.technopolis.server.database.service.impl;

import com.technopolis.server.database.repository.AgentRepository;
import com.technopolis.server.database.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import com.technopolis.server.database.model.Agent;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Autowired
    public AgentServiceImpl(@NotNull final AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Agent getAgent(@NotNull final String name) {
        return this.agentRepository.findAgentByName(name);
    }

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }
}
