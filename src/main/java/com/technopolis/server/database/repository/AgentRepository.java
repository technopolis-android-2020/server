package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query(value = "SELECT * FROM agent WHERE name = ?1", nativeQuery = true)
    Agent getAgentByName(String name);

}
