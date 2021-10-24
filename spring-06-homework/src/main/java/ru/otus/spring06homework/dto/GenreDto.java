package ru.otus.spring06homework.dto;

import lombok.Data;

@Data
public class GenreDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return id + " | " + name;
    }
}
