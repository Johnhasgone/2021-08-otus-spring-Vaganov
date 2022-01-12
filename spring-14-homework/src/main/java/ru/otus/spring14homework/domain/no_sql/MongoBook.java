package ru.otus.spring14homework.domain.no_sql;

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
@Document(value = "book")
public class MongoBook {
    @Id
    private String id;
    private String title;
    private List<MongoAuthor> authors;
    private List<MongoGenre> genres;
}
