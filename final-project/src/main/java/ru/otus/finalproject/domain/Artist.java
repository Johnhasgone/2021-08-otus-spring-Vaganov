package ru.otus.finalproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    private Long id;

    @Column
    private String name;
}
