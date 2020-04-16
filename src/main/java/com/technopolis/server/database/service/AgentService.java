package com.technopolis.server.database.service;

import com.technopolis.server.database.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.technopolis.server.database.model.Agent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    @Autowired
    public AgentService (AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Agent getAgent(String name) {
        return this.agentRepository.findAgentByName(name);
    }

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }
}
