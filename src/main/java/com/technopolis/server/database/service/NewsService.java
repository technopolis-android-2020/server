package com.technopolis.server.database.service;

import com.technopolis.server.database.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.technopolis.server.database.model.News;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService (NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
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
