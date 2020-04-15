package com.technopolis.server.server.controllers;

import com.technopolis.server.server.dto.AuthenticationRequestDto;
import com.technopolis.server.server.dto.RefreshRequestDto;
import com.technopolis.server.server.dto.RegisterRequestDto;
import com.technopolis.server.server.model.User;
import com.technopolis.server.server.security.jwt.JwtTokenProvider;
import com.technopolis.server.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String accessToken = jwtTokenProvider.createToken(username, user.getRoles());
            String refreshToken = jwtTokenProvider.createRefreshToken(username);

            user.setRefreshToken(refreshToken);
            userService.updateUser(user);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        if (userService.findByUsername(username) != null) {
            throw new BadCredentialsException("User with username: " + username + " already exists");
        } else if (userService.findByEmail(email) != null) {
            throw new BadCredentialsException("User with email: " + email + " already exists");
        }

        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setPassword(requestDto.getPassword());
        user.setRefreshToken(refreshToken);

        userService.register(user);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("refreshToken", refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("refresh")
    public ResponseEntity refresh(@RequestBody RefreshRequestDto requestDto) {
        String reqUsername = requestDto.getUsername();
        String reqRefreshToken = requestDto.getRefreshToken();

        User user = userService.findByUsername(reqUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + reqUsername + " not found");
        }
        String userRefreshToken = user.getRefreshToken();
        if (!reqRefreshToken.equals(userRefreshToken)) {
            throw new UsernameNotFoundException("User refresh token not valid");
        }

        String newAccessToken = jwtTokenProvider.createToken(reqUsername, user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", reqUsername);
        response.put("accessToken", newAccessToken);
        return ResponseEntity.ok(response);
    }
}
