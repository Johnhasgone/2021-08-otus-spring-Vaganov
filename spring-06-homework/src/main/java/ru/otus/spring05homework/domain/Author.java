package ru.otus.spring05homework.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    public Author(String authorName) {
        this.name = authorName;
    }

    @Override
    public String toString() {
        return id + " | " + name;
    }

}
