package ru.otus.spring05homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    private Long id;

    @Column(name = "title")
    private final String title;

    @Column(name = "genre_id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private final Genre genre;

    @Column(name = "author_id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private final Author author;

    @Column(name = "comment_id")
    @OneToMany(mappedBy = "book")
    private List<Comment> comments;

    @Override
    public String toString() {
        return id + " | " + title + " | " + author.getName() + " | " + genre.getName();
    }
}
