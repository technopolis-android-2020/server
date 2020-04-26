package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    News findTopByAgent_NameOrderByPublicationDateDesc(String agentName);

    List<News> findNewsByPublicationDateAfter(Date publicationDate);
}
