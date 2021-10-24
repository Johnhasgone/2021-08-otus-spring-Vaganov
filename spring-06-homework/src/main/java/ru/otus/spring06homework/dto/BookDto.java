package ru.otus.spring06homework.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JGlobalMap
public class BookDto {
    private Long id;
    private String title;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;
    private List<CommentDto> comments;

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
                        .collect(Collectors.joining(", ")) +
                "\nComments: " +
                (comments.isEmpty()
                        ? "-"
                        : comments.stream()
                        .map(e -> "\"" + e.getText() + "\"")
                        .collect(Collectors.joining(", "))
                );
    }
}
