package com.technopolis.server.fetcher;

import com.technopolis.server.database.service.AgentService;
import com.technopolis.server.database.service.NewsService;
import com.technopolis.server.database.model.Agent;
import com.technopolis.server.database.model.News;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.slf4j.Logger;

import java.util.concurrent.Callable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.net.URL;

public abstract class Fetcher implements Callable<Integer> {

    private NewsService newsService;
    private AgentService agentService;
    private String rssUrl;
    Logger logger;
    String agentName;

    Fetcher(String rssUrl, AgentService agentService, NewsService newsService) {
        this.rssUrl = rssUrl;
        this.newsService = newsService;
        this.agentService = agentService;
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    @Override
    public Integer call() throws Exception {
        saveNews( makeNews( fetchRss( new URL(rssUrl))));
        return 0;
    }


    // +-------------------------------------------+
    // +            Document Methods               +
    // +-------------------------------------------+

    private Document fetchDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.warn(this.getClass().getName() + ": can't get document.");
            return null;
        }
    }

    private String getTitle(Document newsDocument) {
        String title;

        if ( (title = getNewsTitle(newsDocument)) == null) {
            logger.info(this.getClass().getName() + ": document has null title.");
            return null;
        }

        return title;
    }

    abstract String getNewsTitle(Document newsDocument);

    abstract String getNewsBody(Document newsDocument);


    // +-------------------------------------------+
    // +              News Methods                 +
    // +-------------------------------------------+

    private List<News> makeNews(SyndFeed feed) {
        List<News> result = new ArrayList<>();
        Date latestDate = newsService.getLatestDateByAgentName(agentName);

        logger.info(this.getClass().getName() + ": making news from feed.");

        for (SyndEntry entry: feed.getEntries()) {
            if (latestDate == null || getPublicationDateFromRssEntity(entry).compareTo(latestDate) > 0) {
                result.add(makeNews(entry));
            }
        }

        return result;
    }

    private News makeNews(SyndEntry entry) {
        String url = getUrlFromRssEntity(entry);
        Document document = fetchDocument(url);

        logger.info("RBC_Fetcher: Making news with rss " + url);

        News news = new News();
        news.setPublicationDate(getPublicationDateFromRssEntity(entry));
        news.setTitle(getTitle(document));
        news.setBody(getNewsBody(document));
        news.setAgent(getAgent());
        news.setUrl(getUrlFromRssEntity(entry));

        return news;
    }


    // +-------------------------------------------+
    // +               Rss Methods                 +
    // +-------------------------------------------+

    private SyndFeed fetchRss(URL rssUrl) throws IOException, FeedException {
        logger.info(this.getClass().getName() + ": Fetching rss.");
        return new SyndFeedInput().build(new XmlReader(rssUrl));
    }

    private String getUrlFromRssEntity(SyndEntry entry) {
        String url;

        if ( (url = entry.getLink()) == null) {
            logger.warn(this.getClass().getName() + ": entity has null link");
            return null;
        }

        return url;
    }

    private Date getPublicationDateFromRssEntity(SyndEntry entry) {
        return entry.getPublishedDate() == null ?
                Calendar.getInstance().getTime() :
                entry.getPublishedDate();
    }

    // ---------------------------------------------

    private void saveNews(List<News> news) {
        logger.info(this.getClass().getName() + ": saving news");
        newsService.addNews(news);
    }

    private Agent getAgent() {
        Agent agent;

        if ( (agent = agentService.getAgent(agentName)) == null) {
            logger.warn(this.getClass().getName() + ": agent is null");
            return null;
        }

        return agent;
    }
}