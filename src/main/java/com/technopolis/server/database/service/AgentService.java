package com.technopolis.server.database.service;

import com.technopolis.server.database.model.Agent;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AgentService {

    Agent getAgent(@NotNull final String name);

    List<Agent> findAll();

}
