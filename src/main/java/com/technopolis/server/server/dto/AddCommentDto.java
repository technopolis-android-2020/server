package com.technopolis.server.server.dto;

import lombok.Data;

@Data
public class AddCommentDto {
    private String username;
    private String content;
    private Long newsId;
}
