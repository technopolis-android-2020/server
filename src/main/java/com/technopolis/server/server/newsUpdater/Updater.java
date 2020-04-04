package com.technopolis.server.server.newsUpdater;

public interface Updater extends Runnable {

    void update();

    void setUrl(final String url);
}
