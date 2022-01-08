package ru.otus.spring14homework.service;

import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.sql.Author;

@Service
public class AuthorConvertService implements EntityConvertService<Author, ru.otus.spring14homework.domain.no_sql.Author>{
    @Override
    public ru.otus.spring14homework.domain.no_sql.Author convert(Author source) {
        return new ru.otus.spring14homework.domain.no_sql.Author(source.getName());
    }
}
