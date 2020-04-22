package com.technopolis.server.server.service;

import com.technopolis.server.server.model.Agent;

import java.util.List;

public interface AgentService {

    public Agent getAgent(String name);

    public List<Agent> findAll();

}
