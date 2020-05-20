package com.technopolis.server.database.service;

import com.technopolis.server.database.model.Agent;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AgentService {

    public Agent getAgent(@NotNull final String name);

    public List<Agent> findAll();

}
