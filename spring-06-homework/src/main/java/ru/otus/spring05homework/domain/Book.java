package ru.otus.spring05homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre_id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres;

    @Column(name = "author_id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors;

    @Column(name = "comment_id")
    @OneToMany(mappedBy = "book")
    private List<Comment> comments;

    public Book(Long id, String title, List<Author> authors, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return id +
                " | " +
                title +
                " | " +
                authors.stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(", ")) +
                " | " +
                genres.stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", "));
    }
}
