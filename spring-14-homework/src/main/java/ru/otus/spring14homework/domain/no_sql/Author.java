package ru.otus.spring14homework.domain.no_sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Author {
    @Id
    private String id;
    private String name;

    public Author(String authorName) {
        this.name = authorName;
    }

}
