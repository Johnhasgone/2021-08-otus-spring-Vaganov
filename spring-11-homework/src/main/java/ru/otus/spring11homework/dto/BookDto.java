package ru.otus.spring11homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookDto {
    private String id;
    private String title;
    private String authors;
    private String genres;
}
