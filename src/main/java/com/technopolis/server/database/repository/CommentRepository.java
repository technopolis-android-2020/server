package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.Comment;
import com.technopolis.server.database.model.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findByNews(@NotNull final News news, @NotNull final Sort sort);
}
