package com.technopolis.server.database.service;

import com.technopolis.server.server.dto.AddCommentDto;
import com.technopolis.server.database.model.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CommentService {

    Comment add(@NotNull final AddCommentDto addComment);

    Comment findById(final Long id);

    List<Comment> findAllByNewsId(final Long id);

    void delete(final Long id);

    Comment updateComment(@NotNull final Comment comment);
}
