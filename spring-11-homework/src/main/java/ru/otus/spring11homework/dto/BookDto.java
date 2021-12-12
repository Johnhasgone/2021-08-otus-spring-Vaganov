package ru.otus.spring11homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookDto {
    private String id;
    private String title;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;

    @Override
    public String toString() {
        return id +
                " | " +
                title +
                " | " +
                authors.stream()
                        .map(AuthorDto::getName)
                        .collect(Collectors.joining(", ")) +
                " | " +
                genres.stream()
                        .map(GenreDto::getName)
                        .collect(Collectors.joining(", "));
    }
}
