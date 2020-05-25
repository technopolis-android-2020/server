package com.technopolis.server.fetcher;

import com.rometools.rome.feed.synd.SyndEntry;
import com.technopolis.server.database.service.impl.AgentServiceImpl;
import com.technopolis.server.database.service.impl.NewsServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.validation.constraints.NotNull;

public class VedomostiFetcher extends Fetcher {

    VedomostiFetcher(@NotNull final String rssUrl,
               @NotNull final AgentServiceImpl agentService,
               @NotNull final NewsServiceImpl newsService) {
        super(rssUrl, agentService, newsService);
        this.agentName = "Vedomosti";
    }

    @Override
    String getNewsTitle(@NotNull Document newsDocument) {
        return newsDocument.body().getElementsByClass("article-headline__title").first().text();
    }

    @Override
    String getNewsBody(@NotNull Document newsDocument) {
        Document newDocument = Document.createShell("");

        logger.info("Vedomosti_Fetcher: start handling document.");
        Elements newsParts = newsDocument.body()
                .getElementsByClass("article__content").first()
                .getElementsByClass("article-boxes-list article__boxes");

        for (Element part : newsParts) {
            logger.info("Vedomosti_Fetcher: start handling element");
            part.removeClass("box-inset-media");
            part.removeClass("box-inset-link__card");
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

}
