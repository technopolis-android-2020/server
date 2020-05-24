package com.technopolis.server.fetcher;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Document;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.technopolis.server.database.model.News;
import com.technopolis.server.database.model.Agent;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import com.technopolis.server.database.service.impl.AgentServiceImpl;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.technopolis.server.hekpers.Converters.dateToTimestamp;
import static com.technopolis.server.hekpers.Converters.fromTimestamp;

public abstract class Fetcher implements Callable<Integer> {

    private final AgentServiceImpl agentService;
    private final NewsServiceImpl newsService;
    String channelPreviewImgUrl;
    private final String rssUrl;
    SyndFeed fetchedRss;
    String agentName;
    Logger logger;

    Fetcher(@NotNull final String rssUrl,
            @NotNull final AgentServiceImpl agentService,
            @NotNull final NewsServiceImpl newsService) {
        this.rssUrl = rssUrl;
        this.newsService = newsService;
        this.agentService = agentService;
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    @Override
    public Integer call() throws Exception {
        fetchRss( new URL(rssUrl));
        channelPreviewImgUrl = getChannelPreviewImg();
        saveNews( makeNews());
        return 0;
    }

    // +-------------------------------------------+
    // +            Document Methods               +
    // +-------------------------------------------+

    private Document fetchDocument(@NotNull final String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.warn(this.getClass().getName() + ": can't get document.");
            return null;
        }
    }

    private String getTitle(@NotNull final Document newsDocument) {
        String title;

        if ( (title = getNewsTitle(newsDocument)) == null) {
            logger.info(this.getClass().getName() + ": document has null title.");
            return null;
        }

        return title;
    }

    abstract String getNewsTitle(@NotNull final Document newsDocument);

    abstract String getNewsBody(@NotNull final Document newsDocument);


    // +-------------------------------------------+
    // +              News Methods                 +
    // +-------------------------------------------+

    private List<News> makeNews() {
        List<News> result = new ArrayList<>();
        Long latestDate = newsService.getLatestDateByAgentName(agentName);

        logger.info(this.getClass().getName() + ": making news from feed.");

        for (SyndEntry entry: fetchedRss.getEntries()) {
            if (latestDate == null || getPublicationDateFromRssEntity(entry).compareTo(fromTimestamp(latestDate)) > 0) {
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
        news.setPublicationDate(dateToTimestamp(getPublicationDateFromRssEntity(entry)));
        news.setImageUrl(getPreviewImageFromRssEntity(entry));
        news.setTitle(getTitle(document));
        news.setBody(getNewsBody(document));
        news.setAgent(getAgent());
        news.setUrl(getUrlFromRssEntity(entry));

        return news;
    }


    // +-------------------------------------------+
    // +               Rss Methods                 +
    // +-------------------------------------------+

    private void fetchRss(@NotNull final URL rssUrl) throws IOException, FeedException {
        logger.info(this.getClass().getName() + ": Fetching rss.");
        fetchedRss = new SyndFeedInput().build(new XmlReader(rssUrl));
    }

    private String getUrlFromRssEntity(@NotNull final SyndEntry entry) {
        String url;
        if ( (url = entry.getLink()) == null) {
            logger.warn(this.getClass().getName() + ": entity has null link");
            return null;
        }
        return url;
    }

    private Date getPublicationDateFromRssEntity(@NotNull final SyndEntry entry) {
        return entry.getPublishedDate() == null ?
                Calendar.getInstance().getTime() :
                entry.getPublishedDate();
    }

    abstract String getPreviewImageFromRssEntity(@NotNull final SyndEntry entity);

    abstract String getChannelPreviewImg();

    // ---------------------------------------------

    private void saveNews(@NotNull final List<News> news) {
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
