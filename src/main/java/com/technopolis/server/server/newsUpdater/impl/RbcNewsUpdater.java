package com.technopolis.server.server.newsUpdater.impl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.technopolis.server.server.newsUpdater.Updater;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

public class RbcNewsUpdater implements Updater {

    private String agentUrl;
    private LocalDateTime lastNewsDate;

    @Override
    public void update() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/testdb",
                            "manisha", "123"); //нужна бд для подключения
            c.setAutoCommit(false);

            stmt = c.createStatement();
            if (lastNewsDate == null){
                ResultSet rs = stmt.executeQuery( "SELECT max(date) FROM news;" );
                lastNewsDate = rs.getTimestamp("date").toLocalDateTime();
                rs.close();
            }

            SyndFeed rbcNews = new SyndFeedInput().build(new XmlReader(new URL(agentUrl)));
            for (SyndEntry news : rbcNews.getEntries()) {
                stmt.executeUpdate("INSERT into news (date, agent, title, body, url) values (" +
                        news.getPublishedDate() + ", " +
                        news.getAuthor() + ", " +
                        news.getTitle() + ", " +
                        news.getDescription().getValue() + ", " +
                        news.getUri() + ");");
            }

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public void setUrl(String url) {
        agentUrl = url;
    }

    @SneakyThrows
    @Override
    public void run() {
        update();
        Thread.sleep(1000);
    }
}
