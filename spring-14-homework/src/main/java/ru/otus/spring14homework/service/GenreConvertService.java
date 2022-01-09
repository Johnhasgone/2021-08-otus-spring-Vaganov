package ru.otus.spring14homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring14homework.domain.no_sql.Genre;

@Service
public class GenreConvertService implements EntityConvertService<ru.otus.spring14homework.domain.sql.Genre, Genre>{
    @Override
    public Genre convert(ru.otus.spring14homework.domain.sql.Genre source) {
        return new Genre(source.getName());
    }
}
