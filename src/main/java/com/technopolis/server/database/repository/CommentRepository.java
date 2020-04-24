package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.Comment;
import com.technopolis.server.database.model.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findByNews(News news, Sort sort);
}
