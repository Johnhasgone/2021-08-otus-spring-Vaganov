package ru.otus.spring05homework.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "genre")
public class Genre {
    @Id
    private Long id;

    @Column(name = "name")
    private final String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }
}