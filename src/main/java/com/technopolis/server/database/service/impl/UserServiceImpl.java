package com.technopolis.server.database.service.impl;

import com.technopolis.server.database.model.Role;
import com.technopolis.server.database.model.Status;
import com.technopolis.server.database.model.User;
import com.technopolis.server.database.repository.RoleRepository;
import com.technopolis.server.database.repository.UserRepository;
import com.technopolis.server.database.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@NotNull final UserRepository userRepository,
                           @NotNull final RoleRepository roleRepository,
                           @NotNull final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(@NotNull final User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setRefreshToken(user.getRefreshToken());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

    }

    @Override
    public User updateUser(@NotNull final User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll<User> - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(@NotNull final String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername<User> - user: found by username: {}", username);
        return result;
    }

    @Override
    public User findByEmail(@NotNull final String email) {
        User result = userRepository.findByEmail(email);
        log.info("IN findByEmail<User> - user: {} found by email: {}", result, email);
        return result;
    }


    @Override
    public User findById(final Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById<User> - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById<User> - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
        log.info("IN delete<User> - user with id: {} successfully deleted", id);
    }
}
