package com.technopolis.server.server.service.impl;

import com.technopolis.server.server.dto.AddCommentDto;
import com.technopolis.server.server.model.Comment;
import com.technopolis.server.server.model.News;
import com.technopolis.server.server.model.Status;
import com.technopolis.server.server.model.User;
import com.technopolis.server.server.repository.CommentRepository;
import com.technopolis.server.server.repository.NewsRepository;
import com.technopolis.server.server.repository.UserRepository;
import com.technopolis.server.server.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment add(AddCommentDto addComment) {
        Comment comment = new Comment();

        Long newsId = addComment.getNewsId();
        String username = addComment.getUsername();

        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) {
            log.warn("IN add<Comment> - no news found by id: {}", newsId);
            return null;
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("IN add<Comment> - no user found by username: {}", username);
            return null;
        }

        comment.setNews(news);
        comment.setStatus(Status.ACTIVE);
        comment.setUsername(addComment.getUsername());
        comment.setContent(addComment.getContent());
        comment.setPerson(user);

        Comment addedComment = commentRepository.save(comment);

        log.info("IN add<comment> - comment: {} successfully added", comment);
        return addedComment;
    }

    @Override
    public Comment findById(Long id) {
        Comment result = commentRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById<Comment> - no comment found by id: {}", id);
            return null;
        }

        log.info("IN findById<Comment> - comment: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public List<Comment> findAllByNewsId(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news == null) {
            log.warn("IN findAllByNewsId<Comment> - no news found by id: {}", id);
            return null;
        }

        List<Comment> result = commentRepository.findByNews(news, Sort.by("created"));
        if (result == null) {
            log.info("IN findAllByNewsId<Comment> - : no comment found by news: {}", id);
            return null;
        }

        log.info("IN findAllByNewsId<Comment> - comments found by news: {}");
        return result;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }
}
