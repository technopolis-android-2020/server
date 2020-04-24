package com.technopolis.server.server.controllers;

import com.technopolis.server.database.model.News;
import com.technopolis.server.database.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/news/")
public class NewsRestControllerV1 {

    private final NewsService newsService;

    @Autowired
    public NewsRestControllerV1(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("getAll")
    public ResponseEntity<Map<Integer, Object>> getAllNews() {
        List<News> news = newsService.getAll();

        Map<Integer, Object> response = new HashMap<>();
        int count = 0;
        for (News element : news) {
            Map<Object, Object> oneNews = new HashMap<>();
            oneNews.put("id", element.getId());
            oneNews.put("title", element.getTitle());
            oneNews.put("body", element.getBody());
            oneNews.put("url", element.getUrl());
            oneNews.put("date", element.getPublicationDate());
            oneNews.put("agent", element.getAgent().getName());
            // в будущем надо решить как отправлять комментарии.
            // с новостью или отдельно

            response.put(count++, oneNews);
        }

        return ResponseEntity.ok(response);
    }

}
