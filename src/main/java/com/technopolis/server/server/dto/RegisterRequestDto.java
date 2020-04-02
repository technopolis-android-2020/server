package com.technopolis.server.server.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RegisterRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
