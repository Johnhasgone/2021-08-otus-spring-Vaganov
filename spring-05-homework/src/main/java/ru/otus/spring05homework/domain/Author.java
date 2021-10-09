package ru.otus.spring05homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Author {
    private final Long id;
    private final String name;

}
