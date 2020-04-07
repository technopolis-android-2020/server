package com.technopolis.server.server.newsUpdater.impl;

import com.technopolis.server.server.newsUpdater.Updater;
import com.technopolis.server.server.newsUpdater.UpdaterService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UpdaterServiceImpl implements UpdaterService {

    private final String SERVICE_PROP = "service.properties";
    private final String RSS_LIST = "rssList.txt";
    private long timeout;
    private List<RbcNewsUpdater> updaters = new ArrayList<>();
    private ThreadPoolExecutor executor;
    private Map<String, String> sources;

    @Override
    public void update() {
        for (Updater updater : updaters) {
            executor.submit(updater);
        }
    }

    @Override
    public void addUpdater(String propertyName, RbcNewsUpdater updater) {

    }

    @Override
    public void init() {
        loadProperties();
        getSources();
        for (RbcNewsUpdater updater : updaters) {
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        update();
        Thread.sleep(timeout);
    }

    @SneakyThrows
    private void loadProperties(){
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SERVICE_PROP);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + SERVICE_PROP + "' is not found in the classpath");
        }
        timeout = Integer.parseInt(properties.getProperty("service.timeout"));
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("service.threads")));
    }

    private void getSources(){
        sources = new HashMap<>();
        List<String> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(RSS_LIST))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String source : list) {
            String key = source.substring(0, source.indexOf(":"));
            String value = source.substring(source.indexOf(":") + 2);
            sources.putIfAbsent(key, value);
        }
    }
}
