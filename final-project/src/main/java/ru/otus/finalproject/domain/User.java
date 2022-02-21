package ru.otus.finalproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;

    @Column
    private String fullName;

    @Column
    private String login;
}
