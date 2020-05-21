package com.technopolis.server.fetcher;

import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.validation.constraints.NotNull;


public class RbcFetcher extends Fetcher {

    RbcFetcher(@NotNull final String rssUrl,
               @NotNull final AgentServiceImpl agentService,
               @NotNull final NewsServiceImpl newsService) {
        super(rssUrl, agentService, newsService);
        this.agentName = "RBC";
    }

    @Override
    String getNewsBody(@NotNull final Document newsDocument) {
        Document newDocument = Document.createShell("");

        logger.info("RBC_Fetcher: start handling document.");
        Elements newsParts = newsDocument.body()
                .getElementsByClass("l-col-main").get(0)
                .getElementsByClass("article__content");

        for (Element part : newsParts) {
            logger.info("RBC_Fetcher: start handling element");
            part.removeClass("article__inline-item");
            part.removeClass("banner");
            part.removeClass("r-covid-19");
            part.removeClass("subscribe-infographic");
            newDocument.body().appendChild(part);
        }

        return newDocument.outerHtml();
    }

    @Override
    String getPreviewImageFromRssEntity(@NotNull final SyndEntry entity) {
        return entity.getEnclosures().isEmpty() ? channelPreviewImgUrl : entity.getEnclosures().get(0).getUrl();
    }

    @Override
    String getChannelPreviewImg() {
        return fetchedRss.getImage() == null ? "" : fetchedRss.getImage().getUrl();
    }

    @Override
    String getNewsTitle(@NotNull final Document newsDocument) {
        return newsDocument.body().getElementsByClass("article__header__title").first().text();
    }

}
