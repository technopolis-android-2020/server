package com.technopolis.server.server.service;

import com.technopolis.server.server.dto.AddCommentDto;
import com.technopolis.server.server.model.Comment;
import com.technopolis.server.server.model.User;

import java.util.List;

public interface CommentService {

    Comment add(AddCommentDto addComment);

    Comment findById(Long id);

    List<Comment> findAllByNewsId(Long id);

    void delete(Long id);

    Comment updateComment(Comment comment);
}
