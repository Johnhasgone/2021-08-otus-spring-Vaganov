package ru.otus.spring17homework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "library_user")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private String authority;
}
