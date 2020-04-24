package com.technopolis.server.database.service;

import com.technopolis.server.database.model.Agent;

import java.util.List;

public interface AgentService {

    public Agent getAgent(String name);

    public List<Agent> findAll();

}
