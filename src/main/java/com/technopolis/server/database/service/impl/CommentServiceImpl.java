package com.technopolis.server.database.service.impl;

import com.technopolis.server.database.model.Comment;
import com.technopolis.server.database.model.News;
import com.technopolis.server.database.model.Status;
import com.technopolis.server.database.model.User;
import com.technopolis.server.database.repository.CommentRepository;
import com.technopolis.server.database.repository.NewsRepository;
import com.technopolis.server.database.repository.UserRepository;
import com.technopolis.server.database.service.CommentService;
import com.technopolis.server.server.dto.AddCommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(@NotNull final CommentRepository commentRepository,
                              @NotNull final NewsRepository newsRepository,
                              @NotNull final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment add(@NotNull final AddCommentDto addComment) {
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
        comment.setContent(addComment.getContent());
        comment.setUser(user);
        comment.getUser().setUsername(addComment.getUsername());

        Comment addedComment = commentRepository.save(comment);

        log.info("IN add<comment> - comment: {} successfully added", comment);
        return addedComment;
    }

    @Override
    public Comment findById(final Long id) {
        Comment result = commentRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById<Comment> - no comment found by id: {}", id);
            return null;
        }

        log.info("IN findById<Comment> - comment: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public List<Comment> findAllByNewsId(final Long id) {
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

        log.info("IN findAllByNewsId<Comment> - comments found by news: {}", result.size());
        return result;
    }

    @Override
    public void delete(final Long id) {

    }

    @Override
    public Comment updateComment(@NotNull final Comment comment) {
        return null;
    }
}
