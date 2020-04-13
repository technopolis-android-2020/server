package com.technopolis.server.server.service;

import com.technopolis.server.server.model.User;

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
