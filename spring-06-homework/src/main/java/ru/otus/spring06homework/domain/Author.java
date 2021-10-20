package ru.otus.spring06homework.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
