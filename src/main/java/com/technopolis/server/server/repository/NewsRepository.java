package com.technopolis.server.server.repository;

import com.technopolis.server.server.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    News findTopByAgent_NameOrderByPublicationDateDesc(String agentName);
}
