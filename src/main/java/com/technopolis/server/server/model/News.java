package com.technopolis.server.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "news")
public class News extends BaseEntity {
    @Column(name = "agent", nullable = false)
    private String agent;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "url", nullable = false)
    private String url;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<Comment> comment;
}
