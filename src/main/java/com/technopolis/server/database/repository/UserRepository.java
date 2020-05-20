package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@NotNull final String username);

    User findByEmail(@NotNull final String email);
}
