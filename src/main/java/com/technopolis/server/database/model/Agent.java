package com.technopolis.server.database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agent")
@Data
public class Agent extends BaseEntity {

    public Agent() {

    }

    public Agent(@NotNull final String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "preview_img_url")
    private String previewImageUrl;

}