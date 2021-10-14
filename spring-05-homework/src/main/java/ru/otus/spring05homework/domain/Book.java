package ru.otus.spring05homework.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private Long id;
    private final String title;
    private final Genre genre;
    private final Author author;

    @Override
    public String toString() {
        return id + " | " + title + " | " + author.getName() + " | " + genre.getName();
    }
}
