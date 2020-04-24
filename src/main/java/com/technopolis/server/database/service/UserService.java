package com.technopolis.server.database.service;

import com.technopolis.server.database.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);

    void delete(Long id);

    User updateUser(User user);
}
