package com.technopolis.server.server.dto;

import lombok.Data;

@Data
public class RefreshRequestDto {
    private String username;
    private String refreshToken;
}
