package com.technopolis.server.fetcher;

import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FetcherFactory {

    @Value("#{${rssUrl}}")
    private Map<String, String> rssUrls;
    private final NewsServiceImpl newsService;
    private final AgentServiceImpl agentService;

    @Autowired
    FetcherFactory(@NotNull final AgentServiceImpl agentService,
                   @NotNull final NewsServiceImpl newsService) {
        this.agentService = agentService;
        this.newsService = newsService;
    }

    List<Fetcher> getFetchers() {
        List<Fetcher> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : rssUrls.entrySet()) {
            switch (entry.getKey()){
                case "RBC":
                    result.add( new RbcFetcher(rssUrls.get("RBC"), agentService, newsService));
                    break;
                case "Nplus1":
                    result.add( new Nplus1Fetcher(rssUrls.get("Nplus1"), agentService, newsService));
                    break;
                case "Vedomosti":
                    result.add( new VedomostiFetcher(rssUrls.get("Vedomosti"), agentService, newsService));
                    break;
                case "NakedScience":
                    result.add( new NakedScienceFetcher(rssUrls.get("NakedScience"), agentService, newsService));
                    break;
                case "Habr":
                    result.add( new HabrFetcher(rssUrls.get("Habr"), agentService, newsService));
                    break;
                default:
                    break;
            }
        }
        return result;
    }

}
