package com.technopolis.server.database.service;

import com.technopolis.server.database.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {
    void register(@NotNull final User user);

    List<User> getAll();

    User findByUsername(@NotNull final String username);

    User findByEmail(@NotNull final String email);

    User findById(final Long id);

    void delete(final Long id);

    User updateUser(User user);
}
