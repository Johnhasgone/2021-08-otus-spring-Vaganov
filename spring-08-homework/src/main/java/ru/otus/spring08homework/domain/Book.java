package ru.otus.spring08homework.domain;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
@Document
public class Book {
    @Id
    private String id;
    private String title;

    //@DBRef
    private List<Author> authors;

    //@DBRef
    private List<Genre> genres;
}
