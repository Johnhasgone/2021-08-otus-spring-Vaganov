package ru.otus.spring14homework.domain.no_sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "genre")
public class MongoGenre {
    @Id
    private String id;
    private String name;

    public MongoGenre(String name) {
        this.name = name;
    }
}