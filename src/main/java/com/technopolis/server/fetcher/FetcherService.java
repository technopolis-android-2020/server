package com.technopolis.server.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

@Component
public class FetcherService implements CommandLineRunner {

    private final FetcherFactory fetcherFactory;
    private ExecutorService executor;
    private List<Fetcher> fetchers;
    @Value("${fetcherServiceNumberOfThreads}")
    private int numberOfThreads;
    private Logger logger;
    @Value("${fetcherServiceTimeout}")
    private int timeout;

    @Autowired
    FetcherService(@NotNull final FetcherFactory fetcherFactory) {
        this.fetcherFactory = fetcherFactory;
    }

    @Override
    public void run(String... args) {
        initGlobalVariables();
        runEndlessFetching();
    }

    private void initGlobalVariables() {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.logger = LoggerFactory.getLogger("FetcherService");
        this.fetchers = fetcherFactory.getFetchers();
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

}
