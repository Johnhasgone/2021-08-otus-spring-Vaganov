package ru.otus.spring05homework.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private Long id;
    private final String name;
    private final Genre genre;
    private final Author author;

    @Override
    public String toString() {
        return id + " | " + name + " | " + author.getName() + " | " + genre.getName();
    }
}
