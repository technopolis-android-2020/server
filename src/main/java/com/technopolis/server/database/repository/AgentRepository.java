package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Agent findAgentByName(String name);
}
