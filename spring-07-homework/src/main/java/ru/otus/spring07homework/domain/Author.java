package ru.otus.spring07homework.domain;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
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

}
