package com.technopolis.server.server.service.impl;

import com.technopolis.server.server.model.Role;
import com.technopolis.server.server.model.Status;
import com.technopolis.server.server.model.User;
import com.technopolis.server.server.repository.RoleRepository;
import com.technopolis.server.server.repository.UserRepository;
import com.technopolis.server.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setRefreshToken(user.getRefreshToken());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll<User> - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername<User> - user: found by username: {}", username);
        return result;
    }

    @Override
    public User findByEmail(String email){
        User result = userRepository.findByEmail(email);
        log.info("IN findByEmail<User> - user: {} found by email: {}", result, email);
        return result;
    }


    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById<User> - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById<User> - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete<User> - user with id: {} successfully deleted", id);
    }
}
