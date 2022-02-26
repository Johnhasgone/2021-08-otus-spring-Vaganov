package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    private Long id;

    @Column
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
