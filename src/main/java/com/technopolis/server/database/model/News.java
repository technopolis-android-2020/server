package com.technopolis.server.database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "news")
public class News extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name="date", nullable = false)
    private Date publicationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<Comment> comment;
}
