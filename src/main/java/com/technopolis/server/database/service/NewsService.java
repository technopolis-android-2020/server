package com.technopolis.server.database.service;

import com.technopolis.server.database.model.News;

//в разработке
public interface NewsService {
    News findById(Long id);
}
