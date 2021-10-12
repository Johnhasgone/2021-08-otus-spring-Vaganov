package ru.otus.spring05homework.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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
