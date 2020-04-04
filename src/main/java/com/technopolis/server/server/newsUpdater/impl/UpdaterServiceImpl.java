package com.technopolis.server.server.newsUpdater.impl;

import com.technopolis.server.server.newsUpdater.UpdaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Slf4j
public class UpdaterServiceImpl implements UpdaterService {

    private final long timeout = 1000;
    private List<RbcNewsUpdater> updaters;
    private ThreadPoolExecutor fixedThreadPoolExecutor;

    @Override
    public void update() {
        fixedThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    }

    @Override
    public void addUpdater(String propertyName, RbcNewsUpdater updater) {

    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        update();
    }
}
