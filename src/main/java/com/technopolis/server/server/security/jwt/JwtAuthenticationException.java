package com.technopolis.server.server.security.jwt;

import org.springframework.security.core.AuthenticationException;

import javax.validation.constraints.NotNull;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(@NotNull final String msg,
                                      @NotNull final Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(@NotNull final String msg) {
        super(msg);
    }
}
