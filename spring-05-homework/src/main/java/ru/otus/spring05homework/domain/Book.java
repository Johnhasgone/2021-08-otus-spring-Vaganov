package ru.otus.spring05homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {
    private final Long id;
    private final String name;
    private final Genre genre;
    private final Author author;

}
