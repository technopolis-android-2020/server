package com.technopolis.server.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

@Component
public class FetcherService implements Runnable {

    private FetcherFactory fetcherFactory;
    private ExecutorService executor;
    private List<Fetcher> fetchers;
    private int numberOfThreads;
    @Value("${fetcherServiceTimeout}")
    private int timeout;
    private Logger logger;

    @Autowired
    FetcherService(FetcherFactory fetcherFactory) {
        this.fetcherFactory = fetcherFactory;
    }

    @Override
    public void run() {
        initGlobalVariables();
        runEndlessFetching();
    }

    private void initGlobalVariables() {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.fetchers = fetcherFactory.getFetchers();
        this.logger = LoggerFactory.getLogger("FetcherService");
    }

    private void runEndlessFetching() {
        while (true) {
            try {
                fetch();
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                logger.warn("FetcherService: caught the InterruptedException.");
            }
        }
    }

    private void fetch() throws InterruptedException{
        executor.invokeAll(fetchers);
    }


    // +---------------------------------------------+
    // +                    setters                  +
    // +---------------------------------------------+

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }
}
