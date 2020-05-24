package com.technopolis.server.fetcher;

import com.rometools.rome.feed.synd.SyndEntry;
import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.validation.constraints.NotNull;

public class Nplus1Fetcher extends Fetcher {

    public Nplus1Fetcher(@NotNull final String rssUrl,
                         @NotNull final AgentServiceImpl agentService,
                         @NotNull final NewsServiceImpl newsService) {
        super(rssUrl, agentService, newsService);
        this.agentName = "Nplus1";
    }

    @Override
    String getNewsTitle(@NotNull final SyndEntry entry) {
        String newsTitle = entry.getTitle();
        return newsTitle == null ? "" : newsTitle;
    }

    @Override
    String getNewsBody(@NotNull final Document newsDocument) {
        Document newDocument = Document.createShell("");

        logger.info("Nplus1_Fetcher: start handling document.");
        Elements newsParts = newsDocument.body()
                .getElementsByClass("col col-3").get(0)
                .getElementsByClass("body js-mediator-article");

        for (Element part : newsParts) {
            logger.info("Nplus1_Fetcher: start handling element");
            part.removeClass("article-image");
            part.removeClass("incut incut-gallery");
            part.removeClass("incut incut-quote");
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
