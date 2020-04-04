package com.technopolis.server.server.newsUpdater.impl;

import com.technopolis.server.server.newsUpdater.Updater;

import java.time.LocalDateTime;

public class RbcNewsUpdater implements Updater {

    private String agentUrl;
    private LocalDateTime lastNewsDate;

    @Override
    public void update() {
    }

    @Override
    public void setUrl(String url) {
        agentUrl = url;
    }

    @Override
    public void run() {
        update();
    }
}
