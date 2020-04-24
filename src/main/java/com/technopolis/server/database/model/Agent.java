package com.technopolis.server.database.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "agent")
@Data
public class Agent extends BaseEntity {

    @Column(name = "name")
    private String name;
}