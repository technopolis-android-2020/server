package com.technopolis.server.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import com.technopolis.server.database.service.AgentService;
import com.technopolis.server.database.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FetcherFactory {

    @Value("#{${rssUrl}}")
    private Map<String, String> rssUrls;
    private AgentService agentService;
    private NewsService newsService;

    @Autowired
    FetcherFactory(AgentService agentService, NewsService newsService) {
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
