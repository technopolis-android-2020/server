package com.technopolis.server.server.controllers;

import com.technopolis.server.database.model.Comment;
import com.technopolis.server.database.service.CommentService;
import com.technopolis.server.server.dto.AddCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<Object, Object>> addComment(@RequestBody AddCommentDto requestDto) {
        Comment comment = commentService.add(requestDto);

        Map<Object, Object> response = new HashMap<>();
        fillCommentResponse(comment, response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("byNewsId/{id}")
    public ResponseEntity<Map<Integer, Object>> getCommentsByNews(@PathVariable Long id) {
        List<Comment> comments = commentService.findAllByNewsId(id);

        Map<Integer, Object> response = new HashMap<>();
        int count = 0;
        for (Comment com : comments) {
            Map<Object, Object> comment = new HashMap<>();
            fillCommentResponse(com, comment);

            response.put(count++, comment);
        }

        return ResponseEntity.ok(response);
    }

    private void fillCommentResponse(Comment com, Map<Object, Object> comment) {
        comment.put("username", com.getUser().getUsername());
        comment.put("newId", com.getNews().getId());
        comment.put("content", com.getContent());
        comment.put("time", com.getCreated());
    }
}
