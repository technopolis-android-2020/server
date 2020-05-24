package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    News findTopByAgent_NameOrderByPublicationDateDesc(@NotNull final String agentName);

    List<News> findNewsByPublicationDateAfter(@NotNull final Long publicationDate);
}
