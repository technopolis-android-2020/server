package com.technopolis.server.server.newsUpdater;

import com.technopolis.server.server.newsUpdater.impl.RbcNewsUpdater;

public interface UpdaterService extends Runnable {

    void update();

    void addUpdater(String propertyName, RbcNewsUpdater updater);

    void init();
}
