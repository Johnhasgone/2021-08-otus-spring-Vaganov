package ru.otus.spring05homework.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Genre {
    private Long id;
    private final String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }
}