package com.technopolis.server.server.controllers;

import com.technopolis.server.database.model.Agent;
import com.technopolis.server.database.model.News;
import com.technopolis.server.database.service.AgentService;
import com.technopolis.server.database.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/news/")
public class NewsRestControllerV1 {

    private final NewsService newsService;
    private final AgentService agentService;

    @Autowired
    public NewsRestControllerV1(@NotNull final NewsService newsService,
                                @NotNull final AgentService agentService) {
        this.newsService = newsService;
        this.agentService = agentService;
    }

    @GetMapping("getAllNews")
    public ResponseEntity<List<Object>> getAllNews() {
        List<Object> response = getNewsResponse(newsService.getAllNews());

        return ResponseEntity.ok(response);
    }

    @GetMapping("fromDate/{dateInString}")
    public ResponseEntity<List<Object>> getNewsFromDate(@PathVariable final String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        Date date;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Object> response = getNewsResponse(newsService.getByDate(date));

        return ResponseEntity.ok(response);
    }

    private List<Object> getNewsResponse(@NotNull final List<News> news) {
        List<Object> response = new LinkedList<>();
        for (News element : news) {
            Map<Object, Object> oneNews = new HashMap<>();
            oneNews.put("id", element.getId());
            oneNews.put("title", element.getTitle());
            oneNews.put("body", element.getBody());
            oneNews.put("url", element.getUrl());
            oneNews.put("date", element.getPublicationDate());
            oneNews.put("agent", element.getAgent().getName());
            oneNews.put("logo", element.getImageUrl());
            response.add(oneNews);
        }
        return response;
    }

    @GetMapping("getAllAgents")
    public ResponseEntity<List<Object>> getAllAgents() {
        List<Object> response = getAgentResponse(agentService.findAll());

        return ResponseEntity.ok(response);
    }

    private List<Object> getAgentResponse(@NotNull final List<Agent> agents) {
        List<Object> response = new LinkedList<>();
        for (Agent agent : agents) {
            Map<Object, Object> oneAgent = new HashMap<>();
            oneAgent.put("id", agent.getId());
            oneAgent.put("title", agent.getName());
            oneAgent.put("previewImageUrl", agent.getPreviewImageUrl());
            response.add(oneAgent);
        }
        return response;
    }

}
