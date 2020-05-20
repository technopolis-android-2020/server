package com.technopolis.server.database.repository;

import com.technopolis.server.database.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(@NotNull final String name);
}
