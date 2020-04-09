package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
