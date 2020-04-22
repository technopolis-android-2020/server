package com.technopolis.server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.technopolis.server.server.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Agent findAgentByName(String name);
}
