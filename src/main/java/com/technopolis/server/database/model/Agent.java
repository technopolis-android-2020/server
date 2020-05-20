package com.technopolis.server.database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agent")
@Data
public class Agent extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "preview_img_url")
    private String previewImageUrl;

}