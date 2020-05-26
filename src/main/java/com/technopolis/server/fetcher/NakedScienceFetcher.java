package com.technopolis.server.fetcher;

import com.rometools.rome.feed.synd.SyndEntry;
import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.validation.constraints.NotNull;

public class NakedScienceFetcher extends Fetcher {

    public NakedScienceFetcher(@NotNull String rssUrl,
                               @NotNull AgentServiceImpl agentService,
                               @NotNull NewsServiceImpl newsService) {
        super(rssUrl, agentService, newsService);
        this.agentName = "NakedScience";
    }

    @Override
    String getNewsTitle(@NotNull final SyndEntry entry) {
        String newsTitle = entry.getTitle();
        return newsTitle == null ? "" : newsTitle;
    }

    @Override
    String getNewsBody(@NotNull final Document newsDocument) {
        Document newDocument = Document.createShell("");

        logger.info("NakedScience_Fetcher: start handling document.");
        Elements newsParts = newsDocument.body()
                .getElementsByClass("content").first()
                .getElementsByClass("body");

        for (Element part : newsParts) {
            logger.info("NakedScience_Fetcher: start handling element");
            part.removeClass("ads_single");
            part.removeClass("sidebar-block-item-left shesht-info-block__left");
            newDocument.body().appendChild(part);
        }

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
