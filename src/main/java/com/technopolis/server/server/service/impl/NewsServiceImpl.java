package com.technopolis.server.server.service.impl;

import com.technopolis.server.server.model.News;
import com.technopolis.server.server.repository.NewsRepository;
import com.technopolis.server.server.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News findById(Long id) {
        News result = newsRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById<News> - no news found by id: {}", id);
            return null;
        }

        log.info("IN findById<News> - news: {} found by id: {}", result, id);
        return result;
    }
}
