package com.technopolis.server.database.service;

import com.technopolis.server.database.model.News;

import java.util.List;

//в разработке
public interface NewsService {
    News findById(Long id);

    List<News> getAll();
}
