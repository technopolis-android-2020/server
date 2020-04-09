package com.technopolis.server.database.service;

import com.technopolis.server.database.model.Comment;
import com.technopolis.server.database.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment) {
        this.commentRepository.save(comment);
    }

}
