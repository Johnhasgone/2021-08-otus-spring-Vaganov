package ru.otus.spring05homework.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }
}