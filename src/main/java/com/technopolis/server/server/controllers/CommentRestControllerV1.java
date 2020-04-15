package com.technopolis.server.server.controllers;

import com.technopolis.server.server.dto.AddCommentDto;
import com.technopolis.server.server.model.Comment;
import com.technopolis.server.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/comment/")
public class CommentRestControllerV1 {

    private final CommentService commentService;

    @Autowired
    public CommentRestControllerV1(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("add")
    public ResponseEntity addComment(@RequestBody AddCommentDto requestDto) {
        Comment comment = commentService.add(requestDto);

        if (comment == null) {
            throw new UsernameNotFoundException("no comment has been added");
        }

        Map<Object, Object> response = new HashMap<>();
        response.put("username", comment.getUsername());
        response.put("newId", comment.getNews().getId());
        response.put("content", comment.getContent());
        response.put("time", comment.getCreated());

        return ResponseEntity.ok(response);
    }

    @GetMapping("byNewsId/{id}")
    public ResponseEntity getCommentsByNews(@PathVariable Long id) {
        List<Comment> comments = commentService.findAllByNewsId(id);

        Map<Integer, Object> response = new HashMap<>();
        int count = 0;
        for (Comment com : comments) {
            Map<Object, Object> comment = new HashMap<>();
            comment.put("username", com.getUsername());
            comment.put("newId", com.getNews().getId());
            comment.put("content", com.getContent());
            comment.put("time", com.getCreated());

            response.put(count++, comment);
        }

        return ResponseEntity.ok(response);
    }
}