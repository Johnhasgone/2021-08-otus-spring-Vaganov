package ru.otus.spring10homework.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
public class BookDto {
    private Long id;
    private String title;
    private String authors;
    private String genres;
}
