package ru.otus.spring05homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {
    private final Long id;
    private final String name;
}
