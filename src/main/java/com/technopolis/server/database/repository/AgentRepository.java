package com.technopolis.server.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.technopolis.server.database.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Agent findAgentByName(String name);
}
