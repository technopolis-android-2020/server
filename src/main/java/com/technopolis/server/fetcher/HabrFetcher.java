package com.technopolis.server.fetcher;

import com.rometools.rome.feed.synd.SyndEntry;
import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.validation.constraints.NotNull;

public class HabrFetcher extends Fetcher {

    public HabrFetcher(@NotNull String rssUrl,
                       @NotNull AgentServiceImpl agentService,
                       @NotNull NewsServiceImpl newsService) {
        super(rssUrl, agentService, newsService);
        this.agentName = "Habr";
    }

    @Override
    String getNewsTitle(@NotNull final SyndEntry entry) {
        String newsTitle = entry.getTitle();
        return newsTitle == null ? "" : newsTitle;
    }

    @Override
    String getNewsBody(@NotNull final Document newsDocument) {
        Document newDocument = Document.createShell("");

        logger.info("Habr_Fetcher: start handling document.");
        Element newsParts = newsDocument.body()
                .getElementsByClass("post__wrapper").first()
                .getElementById("post-content-body");

        logger.info("Habr_Fetcher: start handling element");
        newDocument.body().appendChild(newsParts);

        return newDocument.outerHtml();
    }

    @Override
    String getPreviewImageFromRssEntity(@NotNull final SyndEntry entity) {
        if (!entity.getForeignMarkup().isEmpty() && !entity.getForeignMarkup().get(0).getAttributes().isEmpty()) {
            return entity.getForeignMarkup().get(0).getAttributes().get(0).getValue();
        }
        return channelPreviewImgUrl;
    }

    @Override
    String getChannelPreviewImg() {
        return fetchedRss.getImage() == null ? "" : fetchedRss.getImage().getUrl();
    }
}
