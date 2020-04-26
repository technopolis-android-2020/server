package com.technopolis.server.server.controllers;

import com.technopolis.server.database.model.News;
import com.technopolis.server.database.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/news/")
public class NewsRestControllerV1 {

    private final NewsService newsService;

    @Autowired
    public NewsRestControllerV1(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Object>> getAllNews() {
        List<Object> response = getResponse(newsService.getAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("fromDate/{dateInString}")
    public ResponseEntity<List<Object>> getNewsFromDate(@PathVariable String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Object> response = getResponse(newsService.getByDate(date));

        return ResponseEntity.ok(response);
    }

    private List<Object> getResponse(List<News> news) {
        List<Object> response = new LinkedList<>();
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

            response.add(oneNews);
        }
        return response;
    }

}
