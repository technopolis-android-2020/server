package com.technopolis.server.server.fetcher;

import com.technopolis.server.server.service.impl.AgentServiceImpl;
import com.technopolis.server.server.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FetcherFactory {

    @Value("#{${rssUrl}}")
    private Map<String, String> rssUrls;
    private NewsServiceImpl newsService;
    private AgentServiceImpl agentService;

    @Autowired
    FetcherFactory(AgentServiceImpl agentService, NewsServiceImpl newsService) {
        this.agentService = agentService;
        this.newsService = newsService;
    }

    List<Fetcher> getFetchers() {
        List<Fetcher> result = new ArrayList<>();

        if (rssUrls.containsKey("RBC")) {
            result.add( new RbcFetcher(rssUrls.get("RBC"), agentService, newsService) );
        }

        return result;
    }

}
