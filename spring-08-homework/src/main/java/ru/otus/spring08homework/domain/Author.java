package ru.otus.spring08homework.domain;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
@Document
public class Author {
    @Id
    private String id;
    private String name;

    public Author(String authorName) {
        this.name = authorName;
    }

}
