package ru.otus.spring05homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Author {
    private Long id;
    private final String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }

}
