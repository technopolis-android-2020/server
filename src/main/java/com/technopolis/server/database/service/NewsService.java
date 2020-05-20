package com.technopolis.server.database.service;

import com.technopolis.server.database.model.News;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

//в разработке
public interface NewsService {
    News findById(final Long id);

    List<News> getAll();

    List<News> getByDate(@NotNull final Date date);
}
