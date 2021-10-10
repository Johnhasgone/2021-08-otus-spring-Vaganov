package ru.otus.spring05homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
