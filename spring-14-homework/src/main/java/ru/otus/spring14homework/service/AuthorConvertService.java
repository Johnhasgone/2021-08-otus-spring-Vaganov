package ru.otus.spring14homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.no_sql.Author;

@Service
public class AuthorConvertService implements EntityConvertService<ru.otus.spring14homework.domain.sql.Author, Author>{


    @Override
    public Author convert(ru.otus.spring14homework.domain.sql.Author source) {
        return new Author(source.getName());
    }
}
