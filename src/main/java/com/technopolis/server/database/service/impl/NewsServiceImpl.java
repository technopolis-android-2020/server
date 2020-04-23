package com.technopolis.server.database.service.impl;

import com.technopolis.server.database.model.News;
import com.technopolis.server.database.repository.NewsRepository;
import com.technopolis.server.database.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public void addNews(News news) {
        this.newsRepository.saveAndFlush(news);
    }

    public void addNews(List<News> news) {
        this.newsRepository.saveAll(news);
    }

    public Date getLatestDateByAgentName(String agentName) {
        News news = this.newsRepository.findTopByAgent_NameOrderByPublicationDateDesc(agentName);
        return news == null ? null : news.getPublicationDate();
    }
}
